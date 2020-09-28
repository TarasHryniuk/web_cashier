// ----- Custom knockout bindings -----
_(function(observable, observableArray, bindings, extenders, components, isObservable, unwrap, moment, undefined) {

    // Small function to invert boolean observable
    observable.fn.invert = function() {
        var _this = this;
        _this(!_this());
        return _this
    };

    // Array functions

    // Apply auto sorting to ko array, array will resort on every change
    var arrayFn = observableArray.fn;
    arrayFn.autosort = function(field, prepare) {

        var array = this,
            subscription = array.autosort_subscr;

        if (array.autosort_by == field) {
            array.autosort_direction = !array.autosort_direction
        } else {
            array.autosort_direction = true;
            array.autosort_by = field
        }

        change();

        function change() {
            unsubscribe();
            array.sort(sort);
            subscribe()
        }

        function subscribe() {
            array.autosort_subscr = subscription =
                array.subscribe(change);
        }

        function unsubscribe() {
            if (subscription)
                subscription.dispose();
        }

        function sort(a, b) {

            var field = array.autosort_by,
                direction = array.autosort_direction;

            if (a === undefined)
                return direct(1);

            if (b === undefined)
                return direct(-1);

            a = unwrap(field === undefined ? a : a[field]);
            b = unwrap(field === undefined ? b : b[field]);

            if (prepare) {
                a = prepare(a);
                b = prepare(b);
            }

            if (a == b) return 0;
            return direct(a < b ? 1 : -1);

            function direct(result) {
                return direction ? result : -result;
            }
        }

        return array;
    };

    arrayFn.autosort_off = function() {

        var self = this,
            subscription = self.autosort_subscr;

        if (subscription) subscription.dispose();

        delete self.autosort_direction;
        delete self.autosort_subscr;
        delete self.autosort_by;
    };

    // Creates new ko.computed with contend of both ko arrays
    arrayFn.and = function(array) {

        var self = this;

        if (array == undefined)
            return self;

        return ko.computed(function() {
            return unwrap(this.a).concat(unwrap(this.b));
        }, { a: self, b: array });
    };

    arrayFn.pushAll = function(array) {
        ko.utils.arrayPushAll(this(), array);
        this.valueHasMutated();
    };


    // localization
    bindings.lz = wrapBinding('text', function(value) {
        return typeof value === 'string' ?  lz(value) : unwrap(value[lz.locale]);
    });

    // Show in money format
    bindings.money = wrapBinding('text', _.money);

    // Indicates boolean status with icons
    bindings.indicator = wrapBinding('html', function(val, allBindings) {
        var options = $.extend({
            t: "icon-ok text-success",
            f: "icon-remove text-danger"
        }, unwrap(allBindings()));

        return '<span class="' + (val ? options.t : options.f) + '"> </span>'
    });

    // Add calendar to user restrictions without trasformation date
    bindings.date2 = {
        init: function(element, valueAccessor, allBindings) {
            var value = valueAccessor(),
                dateFormat = ko.unwrap(allBindings.get('dateFormat')) || lz.date.pattern,
                watch = allBindings.get('dateWatch'),
                $el = $(element),
                addon = $el.next();

            // when a user changes the date, update the view model
            if (isObservable(value)) {
                ko.utils.registerEventHandler(element, "dp.change", function(event) {
                    var newVal = event.date !== null ? moment(event.date).format('YYYY-MM-DD HH:mm:ss') : null;
                    return value(newVal);
                });
            }


            $el.datetimepicker($.extend({format: dateFormat}, allBindings.get('dateOpt')));
        },

        update: function(element, valueAccessor) {
            var widget = $(element).data("DateTimePicker");
            // when the view model is updated, update the widget
            if (widget) widget.date(ko.utils.unwrapObservable(valueAccessor()) || null);
        }
    };

    // Add calendar to input field
    bindings.date = {
        init: function(element, valueAccessor, allBindings) {
            var value = valueAccessor(),
                dateFormat = ko.unwrap(allBindings.get('dateFormat')) || lz.date.pattern,
                watch = allBindings.get('dateWatch'),
                $el = $(element),
                addon = $el.next();

            // when a user changes the date, update the view model
            if (isObservable(value)) {
                ko.utils.registerEventHandler(element, "dp.change", function(event) {
                    var newVal = event.date.toDate();
                    value(newVal);
                    checkWatches(newVal);
                });
            }

            // Click on input addon will open the calendar
            if (addon.is(".input-group-addon"))
                addon.click(function() { $el.focus() });

            $el.datetimepicker($.extend({format: dateFormat}, allBindings.get('dateOpt')));

            function checkWatches(value) {
                if (!watch) return;

                var period = (watch.period || {num: 1, val: 'month'}),
                    before = watch.before,
                    after = watch.after,
                    time = moment(value), currVal;

                if (isObservable(before)) {

                    currVal = moment(before());
                    if (time.isAfter(currVal)) {
                        before(value);
                    } else if (period) {
                        currVal.subtract(period.num, period.val);
                        if (time.isBefore(currVal)) {
                            before(moment(time).add(period.num, period.val)._d);
                        }
                    }
                }

                if (isObservable(after)) {

                    currVal = moment(after());
                    if (time.isBefore(currVal)) {
                        after(value);
                    } else if (period) {
                        currVal.add(period.num, period.val);
                        if (time.isAfter(currVal)) {
                            after(moment(time).subtract(period.num, period.val)._d);
                        }
                    }
                }
            }
        },

        update: function(element, valueAccessor) {
            var widget = $(element).data("DateTimePicker");
            // when the view model is updated, update the widget
            if (widget) widget.date(ko.utils.unwrapObservable(valueAccessor()) || null);
        }
    };

    bindings.outDate = wrapBinding('text', function(value) {
        return value ? moment(value).format(lz.date.pattern) : "";
    });

    bindings.outZoneDate = wrapBinding('text', function(value) {
        return value ? moment(value).tz("Europe/Kiev").format(lz.date.pattern) : "no date";
    });

    // Add chosen selector to select. Replace selectedOptions and value for select`s.
    bindings.chosen = {
        init: function(element, value, allBindings) {

            var $el = $(element),
                options = allBindings.get('chosenOpt') || {}, //plugin configuration
                deselect_key = 'allow_single_deselect';

            // dynamic additional data for select
            var clientRoles = [];
            $el.find("option").each(function () {
                clientRoles.push($(this).val());
            });
            if (allBindings.has("addData")) {
                var dbRole = ko.unwrap(allBindings.get("addData"));
                    for (var i = 0; i < dbRole.length; i++) {
                        var role = dbRole[i];
                        for (var j = 0; j < clientRoles.length; j++) {
                            if (role === clientRoles[j])
                                break;
                            else if (j === clientRoles.length-1)
                                $el.append("<option value='"+ role.toString() + "'>" + role.toString() + "</option>");
                        }
                    }
            }

            if (options[deselect_key] === undefined) {
                options[deselect_key] = $el.children(":not([value])").length > 0;
            }

            var defaultHandler = bindings[element.multiple ? 'selectedOptions' : 'value'];
            defaultHandler.init.apply(this, arguments);

            $el.chosen(options);

            ['options'].forEach(function(propName) { //  'selectedOptions', 'value'
                if (allBindings.has(propName)) {
                    var prop = allBindings.get(propName);
                    if (isObservable(prop)){
                        prop.subscribe(function(){
                            $el.trigger('chosen:updated');
                        });
                    }
                }
            });
        },

        update: function(element) {

            var defaultHandler = bindings[element.multiple ? 'selectedOptions' : 'value'];
            defaultHandler.update.apply(this, arguments);
            $(element).trigger("chosen:updated");
        },

        after: ['options', 'forEach']
    };

    // Apply auto sort to given ko array
    bindings.autosort = {
        init: function(el, value, allBindings) {
            var val = value(),
                by = allBindings.get('by'),
                type = allBindings.get('type'), fn;

            if (type == 'digit') fn = function(el) { return +el; };
            if (type == 'float') fn = function(el) { return +((el + '').replace(/,/, ".")) };

            $(el).click(function() {
                val.autosort(by, fn);
            });
        }
    };

    /** Helpers urls binding */
    function get_handler_value(handler, value) {
        return typeof handler == "string" ? handler.replace(/%v/g, value) : handler(value);
    }

    bindings.hFilter = {
        update: function(element, valueAccessor, all) {

            var value = valueAccessor(),
                filter = app.model().filter,
                field = filter ? filter[all.get('field')] : null,
                a = $("<a>", {'class': 'hidden-link', title: lz('add_filter')}).text(unwrap(value))[0];

            if (!field)
                return bindings.text.update.call(this, element, valueAccessor);

            a.onclick = function() {
                field(unwrap(all.get('val')));
                app.model().filter.find();

            };

            $(element).html(a);
        }
    };

    bindings.hUrl = {
        init: function(element, valueAccessor, all) {

            var value = valueAccessor(),
                unwrapVal = unwrap(value),
                handlers = bindings.hUrl.handlers[all().type],
                hasHandlers = handlers && handlers.length != 0,
                $el = $(element), a, ul, fn, url_value;

            if (hasHandlers && unwrapVal != null && unwrapVal != '-') {
                a = $('<a>', {'data-toggle': 'dropdown', 'class': 'hidden-link'});
                ul = $('<ul>', {'class': 'dropdown-menu'});

                $el.addClass("dropdown hidden-link").append(a).append(ul);
                fn = update;
            } else fn = defUpdate;

            fn(unwrapVal);
            if (ko.isObservable(value)) {
                value.subscribe(fn);
            }

            function notNull(val) {
                return val == null ? "" : val;
            }

            function defUpdate(val) {
                $el.text(notNull(val));
            }

            function update(val) {
                a.text(notNull(val));
                ul.empty();

                handlers.forEach(function(handler) {
                    url_value = get_handler_value(handler, _.escape(val));
                    if (url_value) ul.append($('<li>').append(url_value));
                });
            }
        },

        handlers: I.hUrl_handlers
    };

    // out an transaction account field
    bindings.ts_account = {

        update: function(element, value, allBindings) {

            var $el = $(element).empty(),
                container = $("<div>", {'class': 'ts-container'}).append("<table>"),
                table = container.find("table"),
                obj = unwrap(value()),
                handlers = allBindings.get('handlers') || bindings.ts_account.handlers;

            if (!obj) return;

            for(var key in obj) {
                var val = _.escape(obj[key]),
                    handler = handlers[key];

                if (key != 'ttn') //Ticket MAN-69
                    table.append('<tr><td>' + key + '</td><td>=</td><td>' + val + '</td></tr>');

                if (handler)
                    $el.append(get_handler_value(handler, val));
            }

            if (!table.is(':empty')) {
                var switcher = $("<a/>");

                function make_btn(visible) {
                    switcher.html('<span title="' + lz(visible ? 'show_all' : 'hide') + '" class="icon-arrow-' + (visible ? 'down' : 'up') + '"></span>');
                }

                $el.append(switcher).append(container.hide());

                ko.utils.registerEventHandler(switcher[0], 'click', function() {
                    var visible = container.is(":visible");
                    make_btn(visible);
                    container[visible ? 'slideUp' : 'slideDown']();
                });

                // visible is inverted in this function
                make_btn(true);
            }
        },

        handlers: lz.ts_account
    };

    bindings.ts_status = wrapBinding('html', function(value) {
        var values = lz.ts_status;
        return (values[value] || (value >= 100 ? values.error : values.unknown )).replace(/%s%/, value);
    });

    // State`s

    function matchElement(el, parent) {
        el.css('position', 'absolute').
            css(parent.position()).
            width(parent.width()).
            height(parent.height());
    }

    bindings.flash = {
        init: function(element, valueAccessor) {

            var $el = $(element),
                value = valueAccessor();

            Object.keys(value).forEach(function(key) {

                var styles = bindings.flash.styles[key];
                value[key].subscribe(function(val) {
                    if (!val) return;

                    var flash = $('<div>', {
                        style: 'opacity: 0.6',
                        'class': styles[0]}
                    );

                    $el.after(flash);
                    matchElement(flash, $el);

                    flash.append(
                        $('<i>', {'class': styles[1]}).
                            css("margin-top", flash.height() / 2 - 48)
                    ).animate({opacity: 0}, {
                        duration: 1200,
                        complete: function() {
                            flash.remove();
                        }
                    })
                })
            })
        },

        styles: {
            error: ['flash alert-danger', 'icon-remove'],
            warning: ['flash alert-warning', 'icon-warning'],
            success: ['flash alert-success', 'icon-ok']
        }
    };

    bindings.hover_loader = {

        init: function(element) {
            $(element).after(
                $("<div>", {'class': 'flash hover-loader'}).
                    append('<i class="fa fa-cog fa-spin"></i>')
            )
        },

        update: function(element, valueAccessor) {

            var $el = $(element),
                value = unwrap(valueAccessor()),
                loader = $el.next('.hover-loader');

            if (value) {
                matchElement(loader, $el);
                loader.find(".fa").css("margin-top", $el.height() / 2 - 48);
            }

            loader.toggle(value);
        }
    };

    bindings.btn_state = {
        init: function(element, valueAccessor) {

            var $el = $(element),
                value = unwrap(valueAccessor()),
                defStyle = $el.attr('class');

            $el.attr('data-loading-text', '<span class="icon-time"></span> ' + lz('btn_loading')).
                attr('data-error-text', '<span class="icon-warning-sign"></span> ' + lz('btn_error')).
                attr('data-success-text', '<span class="icon-ok"></span> ' + lz('btn_success')).
                attr('data-warning-text', '<span class="icon-warning-sign"></span> ' + lz('btn_warning'));

            value.loading.subscribe(subscriber(defStyle, 'loading'));

            value.error.subscribe(subscriber('btn btn-danger', 'error'));

            value.success.subscribe(subscriber('btn btn-success', 'success', reset));

            value.warning.subscribe(subscriber('btn btn-warning', 'warning', reset));

            function subscriber(clazz, state, orAction) {
                return function(value) {
                    if (value) {
                        clear();
                        $el.addClass(clazz).button(state);
                    } else if (orAction) orAction();
                }
            }

            function clear() {
                $el.removeClass();
            }

            function reset() {
                clear();
                $el.addClass(defStyle).button('reset');
            }
        }
    };

    // Typeahead
    var tah = {
        template: {
            source: function(query, result) {
                _.cache("templates?perPage=6&q=" + encodeURIComponent(query), function(list) {
                    result(list.map(function(e) {
                        return { value: e.name }
                    }))
                })
            }
        },

        tmpl_owner: {
            source: function(query, result) {
                var filter = encodeURIComponent(query.split(',')[0]), owner;
                try {
                    owner = app.model().terminal().ownerId();
                    if (owner) filter += '&ownerId=' + owner;
                } catch (e) {
                    console.log(e);
                }

                _.cache("templates?perPage=6&q=" + filter, function(list) {
                    result(list.map(function(e) {
                        return {
                            value: e,
                            text: tah.tmpl_owner.displayFn(e)
                        }
                    }))
                })
            },

            displayKey: 'text',

            displayFn: function(t) {
                return t.id ? (t.name + (t.owner ? (", " + t.owner.login) : '')) : ''
            },

            select: function(value) {
                return {
                    id: value.id,
                    name: value.name,
                    owner: {
                        login: value.owner.login
                    }
                }
            }
        },

        city: {
            source: function(query, result) {
                var get_title = lz.title,
                    query_spl = query.split(', '),
                    city = query_spl[0];

                if (query_spl.length == 1) {
                    _.cache("cities?perPage=6&q=" + encodeURIComponent(city), function(list) {
                        result(list.map(function(e) {
                            return {
                                value: get_title(e.title) + ", " + get_title(e.region.title)
                            }
                        }))
                    })
                } else {
                    _.cache("cities/regions", R.pipe(
                        R.filter(function(e) {
                            var title = e.title;
                            return [title.ua, title.ru, title.en].join('|').toLowerCase().
                                    indexOf(query_spl[1].toLowerCase()) >= 0
                        }),

                        R.map(function(e) {
                            return {
                                value: city + ", " + get_title(e.title)
                            }
                        }),

                        result))
                }
            }
        },

        users: {
            source: function(query, result, role) {
                _.cache("users?perPage=6&login=" + encodeURIComponent(query) + (role ? ('&role=' + role) : ''), function(list) {
                    result(list.map(function(e) {
                        return {value: e.login}
                    }))
                })
            }
        },

        agents: {
            source: function(query, result) {
                bindings.typeahead.handlers.users.source(query, result, 'agent')
            }
        },

        account: {
            source: function(query, result) {
                query = query.toLowerCase();
                result([query, 'postman.id', 'account', 'sender', 'receiver', 'msisdn', 'wallet.id', 'card.number.id'].filter(function(val) {
                    return val.indexOf(query) != -1
                }).map(function(val) {
                    return {value: val};
                }))
            }
        }
    };

    bindings.typeahead = {
        init: function(element, valueAccessor, allBindings) {

            // bindings.value.init.apply(this, arguments); // update performs twice while setting template value
            var $el = $(element),
                value = valueAccessor(),
                handler = tah[allBindings.get("type")],
                display = handler.displayFn;

            if (handler) {
                $el.typeahead(null, handler);
                $(element).typeahead('val', display ? display(value) : value);
            }

            $el.on('typeahead:selected', function(el, selected) {
                var val = selected.value,
                    select = handler.select;

                value(select ? select(val) : val);
            });

            $el.on('typeahead:closed', function(el, selected) {
                !($(this).val()) ? value("") : null;
            });
        },

        update: function(element, valueAccessor, allBindings) {

            var value = unwrap(valueAccessor()),
                type = allBindings.get("type"),
                display = tah[type].displayFn;
            // if (display && typeof value === 'string') return; //hack for tmpl_owner
            $(element).typeahead('val', display ? display(value) : value);
        },

        handlers: tah
    };

    // Add terminals select in div
    bindings.multitype = {

        init: function(element, value, allBindings) {

            var $el = $(element),
                list = value(),
                source = bindings.multitype.handlers[allBindings.get('type')],
                input = $("<input>", {"class": "typeahead"});

            $el.addClass("typeahead-ms").append(input).on('click', 'span.badge', function() {
                list.remove(+$(this).text());
            }).click(function() {
                input.focus();
            });

            input.typeahead(
                {}, source
            ).on('typeahead:selected', function(e, selected) {

                var val = +selected.value;

                if (list.indexOf(val) == -1)
                    list.push(val);

                input.typeahead('val','');
            }).keyup(function() {

                var $el = $(this),
                    length = $el.val().length;

                if (length > 5) {
                    $el.width((length + 1) * 10)
                } else {
                    $el.css('width', 'none')
                }
            }).focus(function() {
                $el.addClass("active");
            }).blur(function() {
                $el.removeClass("active");
            });

            list.subscribe(update_list);
            update_list(list);

            function update_list(list) {
                $el.find(".badge").remove();
                $el.prepend(unwrap(list).map(function(e) {
                    return '<span class="badge">' + e + '</span>';
                }).join(" "));
            }
        },

        handlers: {
            serials: {
                source: function(query, result) {
                    _.cache('terminals/find/' + query, function(data) {
                        result(data.map(function(e) {
                            return {value: "" + e};
                        }));
                    });
                }
            },

            services: {
                source: function(query, result) {
                    if (+query) return result([{value: +query, name: query}]);
                    _.cache('services?perPage=6&q=' + query, function(data) {
                        result(data.map(function(e) {
                            return {value: e.id, name: lz.title(e.title)};
                        }));
                    });
                },

                displayKey: 'name'
            }
        }
    };

    function wrapBinding(type, fn) {
        return {
            init:  bindings[type].init,

            update: function(element, valueAccessor, allBindings) {
                bindings[type].update.call(this, element, function() {
                    return fn.call(this, unwrap(valueAccessor()), allBindings);
                });
            }
        };
    }


    extenders.numeric = function(target, precision) {
        // create a writable computed observable to intercept writes to our observable
        var result = ko.pureComputed({
            read: target,  // always return the original observables value

            write: function(value) {
                value = ('' + value).replace(/[^\d]+/g, '');

                var roundingMultiplier = Math.pow(10, precision),
                    valueToWrite = value === '' ? '' : Math.round(parseFloat(+value) * roundingMultiplier) / roundingMultiplier;

                if (valueToWrite === target()) {
                    target.notifySubscribers(valueToWrite);
                } else {
                    target(valueToWrite);
                }
            }
        }).extend({ notify: 'always' });

        //initialize with current value to make sure it is rounded appropriately
        result(target());

        //return the new computed observable
        return result;
    };


    // Shows paginator with given "paginator" (pages count) and "page"
    components.register('paginator', {
        viewModel: function(params) {
            var self = this;

            self.range = params.range || 5;

            self.pages = isObservable(params.pages) ? params.pages : observable(pages);
            self.current = params.page;

            self.first = function() {
                params.page(0);
            };

            self.last = function() {
                params.page(self.pages() - 1);
            };

            self.go = function(m) {
                params.page(m);
            }
        },

        template: {element: 'paginator'}
    });

    components.register('state', {
        viewModel: function(params) {

            var self = this,
                state = params.state;

            self.loading = state.loading;
            self.success = state.success;
            self.error   = state.error;
            self.warning = state.warning;
            self.errorMsg = state.errorMsg;

            self.closeError = function() {
                self.error(false)
            };
        },

        template: {element: 'page-state'}
    });

}, ko.bindingHandlers, ko.extenders, ko.components, ko.isObservable, ko.unwrap, moment);