/** -------------- Utils -------------- */

function _(fn) {
    var params = arguments;
    fn.apply(this, [ko.observable, ko.observableArray].concat([].last.call(params, params.length - 1)))
}

_(function(observable, observableArray, moment, Object, Array, Date, undefined) {

    var ajax, cache;

    /** -------------- Path params -------------- */

    _.parse = function(str) {
        var data = {},
            name, val;

        if (str) str.split("&").forEach(function(param) {
            param = param.split(/=(.+)/);
            if (param.length < 2) return;

            val = param[1];
            if (val.trim() == "") return;

            if (val === "false") val = false;
            if (val === "true") val = true;

            name = param[0];
            if (name in data) {
                if (data[name].constructor != Array) data[name] = [data[name]];
                data[name].push(val);
            } else {
                data[name] = val;
            }
        });

        return data;
    };

    _.str = function(filter) {

        var expressions = [],
            val, type;

        function _push(k, v) {
            expressions.push(k + "=" + v);
        }

        Object.keys(filter).forEach(function(key) {

            val = filter[key];
            if (val == undefined || val === "") return;

            type = val.constructor;

            if (type == Number && val !== +val)
                return;

            if (type == Date)
                val = moment(val).format(lz.date.pattern);

            if (type == Array) {
                for(var j = 0; j < val.length; j++)
                    _push(key, val[j])
            } else {
                _push(key, val)
            }
        });

        return expressions.join("&");
    };

    /** -------------- Ajax -------------- */

    ajax = $.ajax;

    _.get = function(url, success, error) {
        return ajax({
            url: url,
            success: success,
            error: error
        })
    };

    function post_put(type) {
        return function(url, data, success, error) {
            return ajax({
                type: type,
                url: url,
                data: ko.toJSON(data),
                success: success,
                error: error
            });
        }
    }

    _.post = post_put('POST');
    _.put = post_put('PUT');

    _.del = function(url, success, error) {
        return ajax({
            type: "DELETE",
            url: url,
            success: success,
            error: error
        })
    };

    /** -------------- Ajax cache -------------- */

    cache = _.cache = function(url, success, error) {

        var data = cache.get(url);

        if (data === undefined) {
            _.get(url, function(item) {
                cache.add(url, item);
                success && success(item)
            }, error)
        } else {
            success && success(data.item)
        }
    };

    cache.time = 1000 * 60 * 10; // 10 min

    cache.add = function(url, item) {
        cache.data[url] = {
            item: item,
            time: new Date().getTime()
        }
    };

    cache.get = function(url) {

        var data = cache.data[url],
            time = new Date().getTime();

        if (data !== undefined &&
            (time - data.time < cache.time))
            return data
    };

    cache.clear = function() {
        cache.data = {}
    };

    cache.clear();

    /** -------------- Default getters -------------- */

    _.agents = function() {
        var agents = ko.observableArray();
        _.cache("users?perPage=20000&role=agent", agents);
        return agents
    };

    _.keepSelected = function(array, fn, value) {

        fn = fn || function(obj) {
            return obj == value
        };

        if (!array().find(fn))
            array.push(value)
    };

    _.load = function(context, url, filter, fn, error) {
        if (filter) url += filter;
        context.state.load(
            _.get(url, fn, error)
        );
    };

    _.pages = function(self, url, filter) {
        url += "/pages";
        if (filter) url += filter;
        _.get(url, function(data) {
            self.pages(data.pages);
        });
    };

    /** -------------- Helpers -------------- */

    _.promise = function(fn) {

        var dfd = new $.Deferred();

        if (fn) {
            fn.call(this, dfd.resolve, dfd.reject);
        } else {
            dfd.resolve();
        }

        return dfd.promise();
    };

    _.role_color = function(role) {

        var data = {
            success: ['ADMIN','CALLCENTER','OPERATOR'],
            info:['WEB_TS_CANCEL','WEB_TS_ACCOUNT_EDIT'],
            primary: ['AGENT']
        };

        return Object.keys(data).find(function(key) {
            return data[key].indexOf(role) != -1
        }) || 'default'
    };

    _.apply_def = function(fn, def) {
        return function(data) {
            return fn(data || def)
        }
    };

    _.to = function(model, fn) {
        return function(e) {
            return new model(fn ? fn(e) : e);
        }
    };

    _.getFrom = function(date, shift) {
        var m_date = date === undefined ?
            moment().startOf('day') :
            moment(date, lz.date.pattern);

        if (shift) m_date.add({days: shift});

        return m_date._d;
    };

    _.getTo = function(date, shift) {

        var m_date = date === undefined ?
            moment().endOf('day').add(1, "millisecond") :
            moment(date, lz.date.pattern);

        if (shift) m_date.add({days: shift});

        return m_date._d;
    };

    _.array = function(data) {
        if (data instanceof Array) return data;
        return data === undefined ? [] : [data];
    };

    _.int = function(el) {
        return +el;
    };

    _.getter = function(key) {
        return function(item) {
            return item[key];
        }
    };

    _.setter = function(obs, value) {
        return function() {
            obs(value)
        }
    };

    _.delProp = function() {
        var props = [].slice.call(arguments);
        return function(obj) {
            props.forEach(function(prop) {
                delete obj[prop]
            });

            return obj;
        }
    };

    _.escape = function(string) {
        return String(string).replace(/[&<>"'\/]/g, function (s) {
            return {
                "&": "&amp;",
                "<": "&lt;",
                ">": "&gt;",
                '"': '&quot;',
                "'": '&#39;',
                "/": '&#x2F;'
            }[s];
        });
    };

    _.money = function(num) {
        num = num || 0;
        var cop = Math.abs(num % 100);
        return ('' + (num / 100)).split('.')[0] + ',' + (cop < 10 ? ('0' + cop) : cop)
    };

    _.mask = function(str, mask) {

        str = "" + str;

        var f = "", idx = 0;
        for (var i = 0; i < mask.length; i++) {
            var c = mask[i];
            if (c == '#') {
                f += str[idx];
                idx++;
            } else if (c == 'x') {
                f += 'x';
                idx++;
            } else {
                f += c;
            }
        }

        return f
    };

    _.csv = function(url, filter) {
        return function() {

            var compiled = url + ".csv" + (filter ? filter.str() : ""),
                form = $("<form>", {action: compiled, method: 'post', 'class': 'hidden'});

            $("body").append(form);
            form.submit().remove();

            return false;
        }
    };

    _.file = function(url, filter) {
        return function() {

            var compiled = url + (filter ? filter.str() : ""),
                form = $("<form>", {action: compiled, method: 'post', 'class': 'hidden'});

            $("body").append(form);
            form.submit().remove();

            return false;
        }
    };

    _.valid = function(e) {
        return Checkout($(e.target).closest("form"));
    };

    _.apply = function(obj, field, fn) {

        var val = obj[field];

        if (ko.isObservable(val)) {
            val(fn(val()));
        } else obj[field] = fn(val);

        return obj;
    };

    _.commission = {
        parse: function(str) {

            if (!str) str = '0-8000:0';
            if (typeof str != 'string') return str;

            return observable(new function() {
                var self = this, rules;

                function get_model(from, to, howMuch) {
                    return new Model({from: observable(from), to: observable(to), howMuch: observable(howMuch)})
                }

                rules = self.rules = observableArray(str.split(";").map(function(rule) {
                    rule = rule.split(/[-:]/);
                    return get_model(rule[0], rule[1], rule[2])
                }));

                self.push = function() {
                    rules.push(get_model())
                };

                self.remove = function(model) {
                    rules.remove(model)
                }
            })
        },

        str: function(commission) {
            return ko.toJS(commission).rules.map(function(rule) {
                return (rule.from || 0) + "-" + (rule.to || 0) + ":" + (rule.howMuch || 0);
            }).join(";");
        }
    };

    _.ts_account = function(account) {

        var data = {};

        if(account) account.split(";").forEach(function(e) {
            var exp = e.split("=");
            if (exp.length == 2)
                data[exp[0]] = exp[1]
        });

        return data;
    };

    _.withCopy = function(fn) {
        return function() {
            return fn.call(this, ko.toJS(this))
        }
    };

    _.extend = function(obj) {
        var args = arguments;
        [].last.call(args, args.length - 1).forEach(function(extender) {
            Object.keys(extender).forEach(function(key) {
                obj[key] = extender[key];
            });
        });
        return obj;
    };

    (function(context, fn_str) {

        function isFn(obj) {
            return ''.toString.call(obj) === '[object Function]'
        }

        if (typeof /./ !== fn_str) isFn = function(obj) {
            return typeof obj == fn_str || false
        };

        function isObj(el) {
            return typeof el == 'object'
        }

        function struct(object, expectation) {
            if (expectation === undefined) return object;

            if (expectation && isObj(expectation)) {
                if (expectation && expectation.constructor == Array) {
                    if (object == null) {
                        object = [];
                        if (expectation.length > 1) expectation.forEach(function(e, i) {
                            if (i != 0) object.push(isObj(e) ? $.extend(true, {}, e) : e);
                        });
                    } else {
                        object = object.map(function(element) {
                            return struct(element, expectation[0]);
                        });
                    }
                } else {
                    if (object == null) object = {};
                    Object.keys(expectation).forEach(function(key) {
                        object[key] = struct(object[key], expectation[key]);
                    });
                }

                return object;
            }

            var isObject = isObj(object);
            if (isFn(expectation)) return expectation(object);
            if (!isObject && object != null) return object;
            return expectation && isObj(expectation) ? struct(object, expectation) : expectation;
        }

        context.expect = function(expactation, allowNull) {
            return function(data) {
                return allowNull && data == null ? null : struct(data, expactation);
            }
        }
    })(_, 'function');
}, moment, Object, Array, Date);