/* Bootstrap v3.3.4 (c) 2011-2015 Twitter, Inc. ecadbf550ac5d695306e */
if ("undefined" == typeof jQuery) throw new Error("Bootstrap's JavaScript requires jQuery");
+function (t) {
    "use strict";
    var e = t.fn.jquery.split(" ")[0].split(".");
    if (e[0] < 2 && e[1] < 9 || 1 == e[0] && 9 == e[1] && e[2] < 1) throw new Error("Bootstrap's JavaScript requires jQuery version 1.9.1 or higher")
}(jQuery), +function (t) {
    "use strict";

    function e(e) {
        return this.each(function () {
            var o = t(this), n = o.data("bs.alert");
            n || o.data("bs.alert", n = new i(this)), "string" == typeof e && n[e].call(o)
        })
    }

    var o = '[data-dismiss="alert"]', i = function (e) {
        t(e).on("click", o, this.close)
    };
    i.VERSION = "3.3.2", i.TRANSITION_DURATION = 150, i.prototype.close = function (e) {
        function o() {
            a.detach().trigger("closed.bs.alert").remove()
        }

        var n = t(this), s = n.attr("data-target");
        s || (s = n.attr("href"), s = s && s.replace(/.*(?=#[^\s]*$)/, ""));
        var a = t(s);
        e && e.preventDefault(), a.length || (a = n.closest(".alert")), a.trigger(e = t.Event("close.bs.alert")), e.isDefaultPrevented() || (a.removeClass("in"), t.support.transition && a.hasClass("fade") ? a.one("bsTransitionEnd", o).emulateTransitionEnd(i.TRANSITION_DURATION) : o())
    };
    var n = t.fn.alert;
    t.fn.alert = e, t.fn.alert.Constructor = i, t.fn.alert.noConflict = function () {
        return t.fn.alert = n, this
    }, t(document).on("click.bs.alert.data-api", o, i.prototype.close)
}(jQuery), +function (t) {
    "use strict";

    function e(e) {
        return this.each(function () {
            var i = t(this), n = i.data("bs.button"), s = "object" == typeof e && e;
            n || i.data("bs.button", n = new o(this, s)), "toggle" == e ? n.toggle() : e && n.setState(e)
        })
    }

    var o = function (e, i) {
        this.$element = t(e), this.options = t.extend({}, o.DEFAULTS, i), this.isLoading = !1
    };
    o.VERSION = "3.3.2", o.DEFAULTS = {loadingText: "loading..."}, o.prototype.setState = function (e) {
        var o = "disabled", i = this.$element, n = i.is("input") ? "val" : "html", s = i.data();
        e += "Text", null == s.resetText && i.data("resetText", i[n]()), setTimeout(t.proxy(function () {
            i[n](null == s[e] ? this.options[e] : s[e]), "loadingText" == e ? (this.isLoading = !0, i.addClass(o).attr(o, o)) : this.isLoading && (this.isLoading = !1, i.removeClass(o).removeAttr(o))
        }, this), 0)
    }, o.prototype.toggle = function () {
        var t = !0, e = this.$element.closest('[data-toggle="buttons"]');
        if (e.length) {
            var o = this.$element.find("input");
            "radio" == o.prop("type") && (o.prop("checked") && this.$element.hasClass("active") ? t = !1 : e.find(".active").removeClass("active")), t && o.prop("checked", !this.$element.hasClass("active")).trigger("change")
        } else this.$element.attr("aria-pressed", !this.$element.hasClass("active"));
        t && this.$element.toggleClass("active")
    };
    var i = t.fn.button;
    t.fn.button = e, t.fn.button.Constructor = o, t.fn.button.noConflict = function () {
        return t.fn.button = i, this
    }, t(document).on("click.bs.button.data-api", '[data-toggle^="button"]', function (o) {
        var i = t(o.target);
        i.hasClass("btn") || (i = i.closest(".btn")), e.call(i, "toggle"), o.preventDefault()
    }).on("focus.bs.button.data-api blur.bs.button.data-api", '[data-toggle^="button"]', function (e) {
        t(e.target).closest(".btn").toggleClass("focus", /^focus(in)?$/.test(e.type))
    })
}(jQuery), +function (t) {
    "use strict";

    function e(e) {
        e && 3 === e.which || (t(n).remove(), t(s).each(function () {
            var i = t(this), n = o(i), s = {relatedTarget: this};
            n.hasClass("open") && (n.trigger(e = t.Event("hide.bs.dropdown", s)), e.isDefaultPrevented() || (i.attr("aria-expanded", "false"), n.removeClass("open").trigger("hidden.bs.dropdown", s)))
        }))
    }

    function o(e) {
        var o = e.attr("data-target");
        o || (o = e.attr("href"), o = o && /#[A-Za-z]/.test(o) && o.replace(/.*(?=#[^\s]*$)/, ""));
        var i = o && t(o);
        return i && i.length ? i : e.parent()
    }

    function i(e) {
        return this.each(function () {
            var o = t(this), i = o.data("bs.dropdown");
            i || o.data("bs.dropdown", i = new a(this)), "string" == typeof e && i[e].call(o)
        })
    }

    var n = ".dropdown-backdrop", s = '[data-toggle="dropdown"]', a = function (e) {
        t(e).on("click.bs.dropdown", this.toggle)
    };
    a.VERSION = "3.3.2", a.prototype.toggle = function (i) {
        var n = t(this);
        if (!n.is(".disabled, :disabled")) {
            var s = o(n), a = s.hasClass("open");
            if (e(), !a) {
                "ontouchstart" in document.documentElement && !s.closest(".navbar-nav").length && t('<div class="dropdown-backdrop"/>').insertAfter(t(this)).on("click", e);
                var r = {relatedTarget: this};
                if (s.trigger(i = t.Event("show.bs.dropdown", r)), i.isDefaultPrevented()) return;
                n.trigger("focus").attr("aria-expanded", "true"), s.toggleClass("open").trigger("shown.bs.dropdown", r)
            }
            return !1
        }
    }, a.prototype.keydown = function (e) {
        if (/(38|40|27|32)/.test(e.which) && !/input|textarea/i.test(e.target.tagName)) {
            var i = t(this);
            if (e.preventDefault(), e.stopPropagation(), !i.is(".disabled, :disabled")) {
                var n = o(i), a = n.hasClass("open");
                if (!a && 27 != e.which || a && 27 == e.which) return 27 == e.which && n.find(s).trigger("focus"), i.trigger("click");
                var r = " li:not(.disabled):visible a", l = n.find('[role="menu"]' + r + ', [role="listbox"]' + r);
                if (l.length) {
                    var h = l.index(e.target);
                    38 == e.which && h > 0 && h--, 40 == e.which && h < l.length - 1 && h++, ~h || (h = 0), l.eq(h).trigger("focus")
                }
            }
        }
    };
    var r = t.fn.dropdown;
    t.fn.dropdown = i, t.fn.dropdown.Constructor = a, t.fn.dropdown.noConflict = function () {
        return t.fn.dropdown = r, this
    }, t(document).on("click.bs.dropdown.data-api", e).on("click.bs.dropdown.data-api", ".dropdown form", function (t) {
        t.stopPropagation()
    }).on("click.bs.dropdown.data-api", s, a.prototype.toggle).on("keydown.bs.dropdown.data-api", s, a.prototype.keydown).on("keydown.bs.dropdown.data-api", '[role="menu"]', a.prototype.keydown).on("keydown.bs.dropdown.data-api", '[role="listbox"]', a.prototype.keydown)
}(jQuery), +function (t) {
    "use strict";

    function e(e, i) {
        return this.each(function () {
            var n = t(this), s = n.data("bs.modal"), a = t.extend({}, o.DEFAULTS, n.data(), "object" == typeof e && e);
            s || n.data("bs.modal", s = new o(this, a)), "string" == typeof e ? s[e](i) : a.show && s.show(i)
        })
    }

    var o = function (e, o) {
        this.options = o, this.$body = t(document.body), this.$element = t(e), this.$dialog = this.$element.find(".modal-dialog"), this.$backdrop = null, this.isShown = null, this.originalBodyPad = null, this.scrollbarWidth = 0, this.ignoreBackdropClick = !1, this.options.remote && this.$element.find(".modal-content").load(this.options.remote, t.proxy(function () {
            this.$element.trigger("loaded.bs.modal")
        }, this))
    };
    o.VERSION = "3.3.2", o.TRANSITION_DURATION = 300, o.BACKDROP_TRANSITION_DURATION = 150, o.DEFAULTS = {
        backdrop: !0,
        keyboard: !0,
        show: !0
    }, o.prototype.toggle = function (t) {
        return this.isShown ? this.hide() : this.show(t)
    }, o.prototype.show = function (e) {
        var i = this, n = t.Event("show.bs.modal", {relatedTarget: e});
        this.$element.trigger(n), this.isShown || n.isDefaultPrevented() || (this.isShown = !0, this.checkScrollbar(), this.setScrollbar(), this.$body.addClass("modal-open"), this.escape(), this.resize(), this.$element.on("click.dismiss.bs.modal", '[data-dismiss="modal"]', t.proxy(this.hide, this)), this.$dialog.on("mousedown.dismiss.bs.modal", function () {
            i.$element.one("mouseup.dismiss.bs.modal", function (e) {
                t(e.target).is(i.$element) && (i.ignoreBackdropClick = !0)
            })
        }), this.backdrop(function () {
            var n = t.support.transition && i.$element.hasClass("fade");
            i.$element.parent().length || i.$element.appendTo(i.$body), i.$element.show().scrollTop(0), i.adjustDialog(), n && i.$element[0].offsetWidth, i.$element.addClass("in").attr("aria-hidden", !1), i.enforceFocus();
            var s = t.Event("shown.bs.modal", {relatedTarget: e});
            n ? i.$dialog.one("bsTransitionEnd", function () {
                i.$element.trigger("focus").trigger(s)
            }).emulateTransitionEnd(o.TRANSITION_DURATION) : i.$element.trigger("focus").trigger(s)
        }))
    }, o.prototype.hide = function (e) {
        e && e.preventDefault(), e = t.Event("hide.bs.modal"), this.$element.trigger(e), this.isShown && !e.isDefaultPrevented() && (this.isShown = !1, this.escape(), this.resize(), t(document).off("focusin.bs.modal"), this.$element.removeClass("in").attr("aria-hidden", !0).off("click.dismiss.bs.modal").off("mouseup.dismiss.bs.modal"), this.$dialog.off("mousedown.dismiss.bs.modal"), t.support.transition && this.$element.hasClass("fade") ? this.$element.one("bsTransitionEnd", t.proxy(this.hideModal, this)).emulateTransitionEnd(o.TRANSITION_DURATION) : this.hideModal())
    }, o.prototype.enforceFocus = function () {
        t(document).off("focusin.bs.modal").on("focusin.bs.modal", t.proxy(function (t) {
            this.$element[0] === t.target || this.$element.has(t.target).length || this.$element.trigger("focus")
        }, this))
    }, o.prototype.escape = function () {
        this.isShown && this.options.keyboard ? this.$element.on("keydown.dismiss.bs.modal", t.proxy(function (t) {
            27 == t.which && this.hide()
        }, this)) : this.isShown || this.$element.off("keydown.dismiss.bs.modal")
    }, o.prototype.resize = function () {
        this.isShown ? t(window).on("resize.bs.modal", t.proxy(this.handleUpdate, this)) : t(window).off("resize.bs.modal")
    }, o.prototype.hideModal = function () {
        var t = this;
        this.$element.hide(), this.backdrop(function () {
            t.$body.removeClass("modal-open"), t.resetAdjustments(), t.resetScrollbar(), t.$element.trigger("hidden.bs.modal")
        })
    }, o.prototype.removeBackdrop = function () {
        this.$backdrop && this.$backdrop.remove(), this.$backdrop = null
    }, o.prototype.backdrop = function (e) {
        var i = this, n = this.$element.hasClass("fade") ? "fade" : "";
        if (this.isShown && this.options.backdrop) {
            var s = t.support.transition && n;
            if (this.$backdrop = t('<div class="modal-backdrop ' + n + '" />').appendTo(this.$body), this.$element.on("click.dismiss.bs.modal", t.proxy(function (t) {
                return this.ignoreBackdropClick ? void (this.ignoreBackdropClick = !1) : void (t.target === t.currentTarget && ("static" == this.options.backdrop ? this.$element[0].focus() : this.hide()))
            }, this)), s && this.$backdrop[0].offsetWidth, this.$backdrop.addClass("in"), !e) return;
            s ? this.$backdrop.one("bsTransitionEnd", e).emulateTransitionEnd(o.BACKDROP_TRANSITION_DURATION) : e()
        } else if (!this.isShown && this.$backdrop) {
            this.$backdrop.removeClass("in");
            var a = function () {
                i.removeBackdrop(), e && e()
            };
            t.support.transition && this.$element.hasClass("fade") ? this.$backdrop.one("bsTransitionEnd", a).emulateTransitionEnd(o.BACKDROP_TRANSITION_DURATION) : a()
        } else e && e()
    }, o.prototype.handleUpdate = function () {
        this.adjustDialog()
    }, o.prototype.adjustDialog = function () {
        var t = this.$element[0].scrollHeight > document.documentElement.clientHeight;
        this.$element.css({
            paddingLeft: !this.bodyIsOverflowing && t ? this.scrollbarWidth : "",
            paddingRight: this.bodyIsOverflowing && !t ? this.scrollbarWidth : ""
        })
    }, o.prototype.resetAdjustments = function () {
        this.$element.css({paddingLeft: "", paddingRight: ""})
    }, o.prototype.checkScrollbar = function () {
        var t = window.innerWidth;
        if (!t) {
            var e = document.documentElement.getBoundingClientRect();
            t = e.right - Math.abs(e.left)
        }
        this.bodyIsOverflowing = document.body.clientWidth < t, this.scrollbarWidth = this.measureScrollbar()
    }, o.prototype.setScrollbar = function () {
        var t = parseInt(this.$body.css("padding-right") || 0, 10);
        this.originalBodyPad = document.body.style.paddingRight || "", this.bodyIsOverflowing && this.$body.css("padding-right", t + this.scrollbarWidth)
    }, o.prototype.resetScrollbar = function () {
        this.$body.css("padding-right", this.originalBodyPad)
    }, o.prototype.measureScrollbar = function () {
        var t = document.createElement("div");
        t.className = "modal-scrollbar-measure", this.$body.append(t);
        var e = t.offsetWidth - t.clientWidth;
        return this.$body[0].removeChild(t), e
    };
    var i = t.fn.modal;
    t.fn.modal = e, t.fn.modal.Constructor = o, t.fn.modal.noConflict = function () {
        return t.fn.modal = i, this
    }, t(document).on("click.bs.modal.data-api", '[data-toggle="modal"]', function (o) {
        var i = t(this), n = i.attr("href"), s = t(i.attr("data-target") || n && n.replace(/.*(?=#[^\s]+$)/, "")),
            a = s.data("bs.modal") ? "toggle" : t.extend({remote: !/#/.test(n) && n}, s.data(), i.data());
        i.is("a") && o.preventDefault(), s.one("show.bs.modal", function (t) {
            t.isDefaultPrevented() || s.one("hidden.bs.modal", function () {
                i.is(":visible") && i.trigger("focus")
            })
        }), e.call(s, a, this)
    })
}(jQuery), +function (t) {
    "use strict";

    function e(e) {
        return this.each(function () {
            var i = t(this), n = i.data("bs.tooltip"), s = "object" == typeof e && e;
            (n || !/destroy|hide/.test(e)) && (n || i.data("bs.tooltip", n = new o(this, s)), "string" == typeof e && n[e]())
        })
    }

    var o = function (t, e) {
        this.type = null, this.options = null, this.enabled = null, this.timeout = null, this.hoverState = null, this.$element = null, this.init("tooltip", t, e)
    };
    o.VERSION = "3.3.2", o.TRANSITION_DURATION = 150, o.DEFAULTS = {
        animation: !0,
        placement: "top",
        selector: !1,
        template: '<div class="tooltip" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>',
        trigger: "hover focus",
        title: "",
        delay: 0,
        html: !1,
        container: !1,
        viewport: {selector: "body", padding: 0}
    }, o.prototype.init = function (e, o, i) {
        if (this.enabled = !0, this.type = e, this.$element = t(o), this.options = this.getOptions(i), this.$viewport = this.options.viewport && t(this.options.viewport.selector || this.options.viewport), this.$element[0] instanceof document.constructor && !this.options.selector) throw new Error("`selector` option must be specified when initializing " + this.type + " on the window.document object!");
        for (var n = this.options.trigger.split(" "), s = n.length; s--;) {
            var a = n[s];
            if ("click" == a) this.$element.on("click." + this.type, this.options.selector, t.proxy(this.toggle, this)); else if ("manual" != a) {
                var r = "hover" == a ? "mouseenter" : "focusin", l = "hover" == a ? "mouseleave" : "focusout";
                this.$element.on(r + "." + this.type, this.options.selector, t.proxy(this.enter, this)), this.$element.on(l + "." + this.type, this.options.selector, t.proxy(this.leave, this))
            }
        }
        this.options.selector ? this._options = t.extend({}, this.options, {
            trigger: "manual",
            selector: ""
        }) : this.fixTitle()
    }, o.prototype.getDefaults = function () {
        return o.DEFAULTS
    }, o.prototype.getOptions = function (e) {
        return e = t.extend({}, this.getDefaults(), this.$element.data(), e), e.delay && "number" == typeof e.delay && (e.delay = {
            show: e.delay,
            hide: e.delay
        }), e
    }, o.prototype.getDelegateOptions = function () {
        var e = {}, o = this.getDefaults();
        return this._options && t.each(this._options, function (t, i) {
            o[t] != i && (e[t] = i)
        }), e
    }, o.prototype.enter = function (e) {
        var o = e instanceof this.constructor ? e : t(e.currentTarget).data("bs." + this.type);
        return o && o.$tip && o.$tip.is(":visible") ? void (o.hoverState = "in") : (o || (o = new this.constructor(e.currentTarget, this.getDelegateOptions()), t(e.currentTarget).data("bs." + this.type, o)), clearTimeout(o.timeout), o.hoverState = "in", o.options.delay && o.options.delay.show ? void (o.timeout = setTimeout(function () {
            "in" == o.hoverState && o.show()
        }, o.options.delay.show)) : o.show())
    }, o.prototype.leave = function (e) {
        var o = e instanceof this.constructor ? e : t(e.currentTarget).data("bs." + this.type);
        return o || (o = new this.constructor(e.currentTarget, this.getDelegateOptions()), t(e.currentTarget).data("bs." + this.type, o)), clearTimeout(o.timeout), o.hoverState = "out", o.options.delay && o.options.delay.hide ? void (o.timeout = setTimeout(function () {
            "out" == o.hoverState && o.hide()
        }, o.options.delay.hide)) : o.hide()
    }, o.prototype.show = function () {
        var e = t.Event("show.bs." + this.type);
        if (this.hasContent() && this.enabled) {
            this.$element.trigger(e);
            var i = t.contains(this.$element[0].ownerDocument.documentElement, this.$element[0]);
            if (e.isDefaultPrevented() || !i) return;
            var n = this, s = this.tip(), a = this.getUID(this.type);
            this.setContent(), s.attr("id", a), this.$element.attr("aria-describedby", a), this.options.animation && s.addClass("fade");
            var r = "function" == typeof this.options.placement ? this.options.placement.call(this, s[0], this.$element[0]) : this.options.placement,
                l = /\s?auto?\s?/i, h = l.test(r);
            h && (r = r.replace(l, "") || "top"), s.detach().css({
                top: 0,
                left: 0,
                display: "block"
            }).addClass(r).data("bs." + this.type, this), this.options.container ? s.appendTo(this.options.container) : s.insertAfter(this.$element);
            var d = this.getPosition(), p = s[0].offsetWidth, c = s[0].offsetHeight;
            if (h) {
                var f = r, u = this.options.container ? t(this.options.container) : this.$element.parent(),
                    g = this.getPosition(u);
                r = "bottom" == r && d.bottom + c > g.bottom ? "top" : "top" == r && d.top - c < g.top ? "bottom" : "right" == r && d.right + p > g.width ? "left" : "left" == r && d.left - p < g.left ? "right" : r, s.removeClass(f).addClass(r)
            }
            var m = this.getCalculatedOffset(r, d, p, c);
            this.applyPlacement(m, r);
            var v = function () {
                var t = n.hoverState;
                n.$element.trigger("shown.bs." + n.type), n.hoverState = null, "out" == t && n.leave(n)
            };
            t.support.transition && this.$tip.hasClass("fade") ? s.one("bsTransitionEnd", v).emulateTransitionEnd(o.TRANSITION_DURATION) : v()
        }
    }, o.prototype.applyPlacement = function (e, o) {
        var i = this.tip(), n = i[0].offsetWidth, s = i[0].offsetHeight, a = parseInt(i.css("margin-top"), 10),
            r = parseInt(i.css("margin-left"), 10);
        isNaN(a) && (a = 0), isNaN(r) && (r = 0), e.top = e.top + a, e.left = e.left + r, t.offset.setOffset(i[0], t.extend({
            using: function (t) {
                i.css({top: Math.round(t.top), left: Math.round(t.left)})
            }
        }, e), 0), i.addClass("in");
        var l = i[0].offsetWidth, h = i[0].offsetHeight;
        "top" == o && h != s && (e.top = e.top + s - h);
        var d = this.getViewportAdjustedDelta(o, e, l, h);
        d.left ? e.left += d.left : e.top += d.top;
        var p = /top|bottom/.test(o), c = p ? 2 * d.left - n + l : 2 * d.top - s + h,
            f = p ? "offsetWidth" : "offsetHeight";
        i.offset(e), this.replaceArrow(c, i[0][f], p)
    }, o.prototype.replaceArrow = function (t, e, o) {
        this.arrow().css(o ? "left" : "top", 50 * (1 - t / e) + "%").css(o ? "top" : "left", "")
    }, o.prototype.setContent = function () {
        var t = this.tip(), e = this.getTitle();
        t.find(".tooltip-inner")[this.options.html ? "html" : "text"](e), t.removeClass("fade in top bottom left right")
    }, o.prototype.hide = function (e) {
        function i() {
            "in" != n.hoverState && s.detach(), n.$element.removeAttr("aria-describedby").trigger("hidden.bs." + n.type), e && e()
        }

        var n = this, s = t(this.$tip), a = t.Event("hide.bs." + this.type);
        return this.$element.trigger(a), a.isDefaultPrevented() ? void 0 : (s.removeClass("in"), t.support.transition && s.hasClass("fade") ? s.one("bsTransitionEnd", i).emulateTransitionEnd(o.TRANSITION_DURATION) : i(), this.hoverState = null, this)
    }, o.prototype.fixTitle = function () {
        var t = this.$element;
        (t.attr("title") || "string" != typeof t.attr("data-original-title")) && t.attr("data-original-title", t.attr("title") || "").attr("title", "")
    }, o.prototype.hasContent = function () {
        return this.getTitle()
    }, o.prototype.getPosition = function (e) {
        e = e || this.$element;
        var o = e[0], i = "BODY" == o.tagName, n = o.getBoundingClientRect();
        null == n.width && (n = t.extend({}, n, {width: n.right - n.left, height: n.bottom - n.top}));
        var s = i ? {top: 0, left: 0} : e.offset(),
            a = {scroll: i ? document.documentElement.scrollTop || document.body.scrollTop : e.scrollTop()},
            r = i ? {width: t(window).width(), height: t(window).height()} : null;
        return t.extend({}, n, a, r, s)
    }, o.prototype.getCalculatedOffset = function (t, e, o, i) {
        return "bottom" == t ? {
            top: e.top + e.height,
            left: e.left + e.width / 2 - o / 2
        } : "top" == t ? {
            top: e.top - i,
            left: e.left + e.width / 2 - o / 2
        } : "left" == t ? {top: e.top + e.height / 2 - i / 2, left: e.left - o} : {
            top: e.top + e.height / 2 - i / 2,
            left: e.left + e.width
        }
    }, o.prototype.getViewportAdjustedDelta = function (t, e, o, i) {
        var n = {top: 0, left: 0};
        if (!this.$viewport) return n;
        var s = this.options.viewport && this.options.viewport.padding || 0, a = this.getPosition(this.$viewport);
        if (/right|left/.test(t)) {
            var r = e.top - s - a.scroll, l = e.top + s - a.scroll + i;
            r < a.top ? n.top = a.top - r : l > a.top + a.height && (n.top = a.top + a.height - l)
        } else {
            var h = e.left - s, d = e.left + s + o;
            h < a.left ? n.left = a.left - h : d > a.width && (n.left = a.left + a.width - d)
        }
        return n
    }, o.prototype.getTitle = function () {
        var t, e = this.$element, o = this.options;
        return t = e.attr("data-original-title") || ("function" == typeof o.title ? o.title.call(e[0]) : o.title)
    }, o.prototype.getUID = function (t) {
        do t += ~~(1e6 * Math.random()); while (document.getElementById(t));
        return t
    }, o.prototype.tip = function () {
        return this.$tip = this.$tip || t(this.options.template)
    }, o.prototype.arrow = function () {
        return this.$arrow = this.$arrow || this.tip().find(".tooltip-arrow")
    }, o.prototype.enable = function () {
        this.enabled = !0
    }, o.prototype.disable = function () {
        this.enabled = !1
    }, o.prototype.toggleEnabled = function () {
        this.enabled = !this.enabled
    }, o.prototype.toggle = function (e) {
        var o = this;
        e && (o = t(e.currentTarget).data("bs." + this.type), o || (o = new this.constructor(e.currentTarget, this.getDelegateOptions()), t(e.currentTarget).data("bs." + this.type, o))), o.tip().hasClass("in") ? o.leave(o) : o.enter(o)
    }, o.prototype.destroy = function () {
        var t = this;
        clearTimeout(this.timeout), this.hide(function () {
            t.$element.off("." + t.type).removeData("bs." + t.type)
        })
    };
    var i = t.fn.tooltip;
    t.fn.tooltip = e, t.fn.tooltip.Constructor = o, t.fn.tooltip.noConflict = function () {
        return t.fn.tooltip = i, this
    }
}(jQuery), +function (t) {
    "use strict";

    function e(e) {
        return this.each(function () {
            var i = t(this), n = i.data("bs.popover"), s = "object" == typeof e && e;
            (n || !/destroy|hide/.test(e)) && (n || i.data("bs.popover", n = new o(this, s)), "string" == typeof e && n[e]())
        })
    }

    var o = function (t, e) {
        this.init("popover", t, e)
    };
    if (!t.fn.tooltip) throw new Error("Popover requires tooltip.js");
    o.VERSION = "3.3.2", o.DEFAULTS = t.extend({}, t.fn.tooltip.Constructor.DEFAULTS, {
        placement: "right",
        trigger: "click",
        content: "",
        template: '<div class="popover" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>'
    }), o.prototype = t.extend({}, t.fn.tooltip.Constructor.prototype), o.prototype.constructor = o, o.prototype.getDefaults = function () {
        return o.DEFAULTS
    }, o.prototype.setContent = function () {
        var t = this.tip(), e = this.getTitle(), o = this.getContent();
        t.find(".popover-title")[this.options.html ? "html" : "text"](e), t.find(".popover-content").children().detach().end()[this.options.html ? "string" == typeof o ? "html" : "append" : "text"](o), t.removeClass("fade top bottom left right in"), t.find(".popover-title").html() || t.find(".popover-title").hide()
    }, o.prototype.hasContent = function () {
        return this.getTitle() || this.getContent()
    }, o.prototype.getContent = function () {
        var t = this.$element, e = this.options;
        return t.attr("data-content") || ("function" == typeof e.content ? e.content.call(t[0]) : e.content)
    }, o.prototype.arrow = function () {
        return this.$arrow = this.$arrow || this.tip().find(".arrow")
    };
    var i = t.fn.popover;
    t.fn.popover = e, t.fn.popover.Constructor = o, t.fn.popover.noConflict = function () {
        return t.fn.popover = i, this
    }
}(jQuery), +function (t) {
    "use strict";

    function e(e) {
        return this.each(function () {
            var i = t(this), n = i.data("bs.tab");
            n || i.data("bs.tab", n = new o(this)), "string" == typeof e && n[e]()
        })
    }

    var o = function (e) {
        this.element = t(e)
    };
    o.VERSION = "3.3.2", o.TRANSITION_DURATION = 150, o.prototype.show = function () {
        var e = this.element, o = e.closest("ul:not(.dropdown-menu)"), i = e.data("target");
        if (i || (i = e.attr("href"), i = i && i.replace(/.*(?=#[^\s]*$)/, "")), !e.parent("li").hasClass("active")) {
            var n = o.find(".active:last a"), s = t.Event("hide.bs.tab", {relatedTarget: e[0]}),
                a = t.Event("show.bs.tab", {relatedTarget: n[0]});
            if (n.trigger(s), e.trigger(a), !a.isDefaultPrevented() && !s.isDefaultPrevented()) {
                var r = t(i);
                this.activate(e.closest("li"), o), this.activate(r, r.parent(), function () {
                    n.trigger({type: "hidden.bs.tab", relatedTarget: e[0]}), e.trigger({
                        type: "shown.bs.tab",
                        relatedTarget: n[0]
                    })
                })
            }
        }
    }, o.prototype.activate = function (e, i, n) {
        function s() {
            a.removeClass("active").find("> .dropdown-menu > .active").removeClass("active").end().find('[data-toggle="tab"]').attr("aria-expanded", !1), e.addClass("active").find('[data-toggle="tab"]').attr("aria-expanded", !0), r ? (e[0].offsetWidth, e.addClass("in")) : e.removeClass("fade"), e.parent(".dropdown-menu").length && e.closest("li.dropdown").addClass("active").end().find('[data-toggle="tab"]').attr("aria-expanded", !0), n && n()
        }

        var a = i.find("> .active"),
            r = n && t.support.transition && (a.length && a.hasClass("fade") || !!i.find("> .fade").length);
        a.length && r ? a.one("bsTransitionEnd", s).emulateTransitionEnd(o.TRANSITION_DURATION) : s(), a.removeClass("in")
    };
    var i = t.fn.tab;
    t.fn.tab = e, t.fn.tab.Constructor = o, t.fn.tab.noConflict = function () {
        return t.fn.tab = i, this
    };
    var n = function (o) {
        o.preventDefault(), e.call(t(this), "show")
    };
    t(document).on("click.bs.tab.data-api", '[data-toggle="tab"]', n).on("click.bs.tab.data-api", '[data-toggle="pill"]', n)
}(jQuery), +function (t) {
    "use strict";

    function e(e) {
        return this.each(function () {
            var i = t(this), n = i.data("bs.affix"), s = "object" == typeof e && e;
            n || i.data("bs.affix", n = new o(this, s)), "string" == typeof e && n[e]()
        })
    }

    var o = function (e, i) {
        this.options = t.extend({}, o.DEFAULTS, i), this.$target = t(this.options.target).on("scroll.bs.affix.data-api", t.proxy(this.checkPosition, this)).on("click.bs.affix.data-api", t.proxy(this.checkPositionWithEventLoop, this)), this.$element = t(e), this.affixed = null, this.unpin = null, this.pinnedOffset = null, this.checkPosition()
    };
    o.VERSION = "3.3.2", o.RESET = "affix affix-top affix-bottom", o.DEFAULTS = {
        offset: 0,
        target: window
    }, o.prototype.getState = function (t, e, o, i) {
        var n = this.$target.scrollTop(), s = this.$element.offset(), a = this.$target.height();
        if (null != o && "top" == this.affixed) return o > n ? "top" : !1;
        if ("bottom" == this.affixed) return null != o ? n + this.unpin <= s.top ? !1 : "bottom" : t - i >= n + a ? !1 : "bottom";
        var r = null == this.affixed, l = r ? n : s.top, h = r ? a : e;
        return null != o && o >= n ? "top" : null != i && l + h >= t - i ? "bottom" : !1
    }, o.prototype.getPinnedOffset = function () {
        if (this.pinnedOffset) return this.pinnedOffset;
        this.$element.removeClass(o.RESET).addClass("affix");
        var t = this.$target.scrollTop(), e = this.$element.offset();
        return this.pinnedOffset = e.top - t
    }, o.prototype.checkPositionWithEventLoop = function () {
        setTimeout(t.proxy(this.checkPosition, this), 1)
    }, o.prototype.checkPosition = function () {
        if (this.$element.is(":visible")) {
            var e = this.$element.height(), i = this.options.offset, n = i.top, s = i.bottom,
                a = t(document.body).height();
            "object" != typeof i && (s = n = i), "function" == typeof n && (n = i.top(this.$element)), "function" == typeof s && (s = i.bottom(this.$element));
            var r = this.getState(a, e, n, s);
            if (this.affixed != r) {
                null != this.unpin && this.$element.css("top", "");
                var l = "affix" + (r ? "-" + r : ""), h = t.Event(l + ".bs.affix");
                if (this.$element.trigger(h), h.isDefaultPrevented()) return;
                this.affixed = r, this.unpin = "bottom" == r ? this.getPinnedOffset() : null, this.$element.removeClass(o.RESET).addClass(l).trigger(l.replace("affix", "affixed") + ".bs.affix")
            }
            "bottom" == r && this.$element.offset({top: a - e - s})
        }
    };
    var i = t.fn.affix;
    t.fn.affix = e, t.fn.affix.Constructor = o, t.fn.affix.noConflict = function () {
        return t.fn.affix = i, this
    }, t(window).on("load", function () {
        t('[data-spy="affix"]').each(function () {
            var o = t(this), i = o.data();
            i.offset = i.offset || {}, null != i.offsetBottom && (i.offset.bottom = i.offsetBottom), null != i.offsetTop && (i.offset.top = i.offsetTop), e.call(o, i)
        })
    })
}(jQuery), +function (t) {
    "use strict";

    function e(e) {
        var o, i = e.attr("data-target") || (o = e.attr("href")) && o.replace(/.*(?=#[^\s]+$)/, "");
        return t(i)
    }

    function o(e) {
        return this.each(function () {
            var o = t(this), n = o.data("bs.collapse"),
                s = t.extend({}, i.DEFAULTS, o.data(), "object" == typeof e && e);
            !n && s.toggle && /show|hide/.test(e) && (s.toggle = !1), n || o.data("bs.collapse", n = new i(this, s)), "string" == typeof e && n[e]()
        })
    }

    var i = function (e, o) {
        this.$element = t(e), this.options = t.extend({}, i.DEFAULTS, o), this.$trigger = t('[data-toggle="collapse"][href="#' + e.id + '"],[data-toggle="collapse"][data-target="#' + e.id + '"]'), this.transitioning = null, this.options.parent ? this.$parent = this.getParent() : this.addAriaAndCollapsedClass(this.$element, this.$trigger), this.options.toggle && this.toggle()
    };
    i.VERSION = "3.3.2", i.TRANSITION_DURATION = 350, i.DEFAULTS = {toggle: !0}, i.prototype.dimension = function () {
        var t = this.$element.hasClass("width");
        return t ? "width" : "height"
    }, i.prototype.show = function () {
        if (!this.transitioning && !this.$element.hasClass("in")) {
            var e, n = this.$parent && this.$parent.children(".panel").children(".in, .collapsing");
            if (!(n && n.length && (e = n.data("bs.collapse"), e && e.transitioning))) {
                var s = t.Event("show.bs.collapse");
                if (this.$element.trigger(s), !s.isDefaultPrevented()) {
                    n && n.length && (o.call(n, "hide"), e || n.data("bs.collapse", null));
                    var a = this.dimension();
                    this.$element.removeClass("collapse").addClass("collapsing")[a](0).attr("aria-expanded", !0), this.$trigger.removeClass("collapsed").attr("aria-expanded", !0), this.transitioning = 1;
                    var r = function () {
                        this.$element.removeClass("collapsing").addClass("collapse in")[a](""), this.transitioning = 0, this.$element.trigger("shown.bs.collapse")
                    };
                    if (!t.support.transition) return r.call(this);
                    var l = t.camelCase(["scroll", a].join("-"));
                    this.$element.one("bsTransitionEnd", t.proxy(r, this)).emulateTransitionEnd(i.TRANSITION_DURATION)[a](this.$element[0][l])
                }
            }
        }
    }, i.prototype.hide = function () {
        if (!this.transitioning && this.$element.hasClass("in")) {
            var e = t.Event("hide.bs.collapse");
            if (this.$element.trigger(e), !e.isDefaultPrevented()) {
                var o = this.dimension();
                this.$element[o](this.$element[o]())[0].offsetHeight, this.$element.addClass("collapsing").removeClass("collapse in").attr("aria-expanded", !1), this.$trigger.addClass("collapsed").attr("aria-expanded", !1), this.transitioning = 1;
                var n = function () {
                    this.transitioning = 0, this.$element.removeClass("collapsing").addClass("collapse").trigger("hidden.bs.collapse")
                };
                return t.support.transition ? void this.$element[o](0).one("bsTransitionEnd", t.proxy(n, this)).emulateTransitionEnd(i.TRANSITION_DURATION) : n.call(this)
            }
        }
    }, i.prototype.toggle = function () {
        this[this.$element.hasClass("in") ? "hide" : "show"]()
    }, i.prototype.getParent = function () {
        return t(this.options.parent).find('[data-toggle="collapse"][data-parent="' + this.options.parent + '"]').each(t.proxy(function (o, i) {
            var n = t(i);
            this.addAriaAndCollapsedClass(e(n), n)
        }, this)).end()
    }, i.prototype.addAriaAndCollapsedClass = function (t, e) {
        var o = t.hasClass("in");
        t.attr("aria-expanded", o), e.toggleClass("collapsed", !o).attr("aria-expanded", o)
    };
    var n = t.fn.collapse;
    t.fn.collapse = o, t.fn.collapse.Constructor = i, t.fn.collapse.noConflict = function () {
        return t.fn.collapse = n, this
    }, t(document).on("click.bs.collapse.data-api", '[data-toggle="collapse"]', function (i) {
        var n = t(this);
        n.attr("data-target") || i.preventDefault();
        var s = e(n), a = s.data("bs.collapse"), r = a ? "toggle" : n.data();
        o.call(s, r)
    })
}(jQuery), +function (t) {
    "use strict";

    function e(o, i) {
        this.$body = t(document.body), this.$scrollElement = t(t(o).is(document.body) ? window : o), this.options = t.extend({}, e.DEFAULTS, i), this.selector = (this.options.target || "") + " .nav li > a", this.offsets = [], this.targets = [], this.activeTarget = null, this.scrollHeight = 0, this.$scrollElement.on("scroll.bs.scrollspy", t.proxy(this.process, this)), this.refresh(), this.process()
    }

    function o(o) {
        return this.each(function () {
            var i = t(this), n = i.data("bs.scrollspy"), s = "object" == typeof o && o;
            n || i.data("bs.scrollspy", n = new e(this, s)), "string" == typeof o && n[o]()
        })
    }

    e.VERSION = "3.3.2", e.DEFAULTS = {offset: 10}, e.prototype.getScrollHeight = function () {
        return this.$scrollElement[0].scrollHeight || Math.max(this.$body[0].scrollHeight, document.documentElement.scrollHeight)
    }, e.prototype.refresh = function () {
        var e = this, o = "offset", i = 0;
        this.offsets = [], this.targets = [], this.scrollHeight = this.getScrollHeight(), t.isWindow(this.$scrollElement[0]) || (o = "position", i = this.$scrollElement.scrollTop()), this.$body.find(this.selector).map(function () {
            var e = t(this), n = e.data("target") || e.attr("href"), s = /^#./.test(n) && t(n);
            return s && s.length && s.is(":visible") && [[s[o]().top + i, n]] || null
        }).sort(function (t, e) {
            return t[0] - e[0]
        }).each(function () {
            e.offsets.push(this[0]), e.targets.push(this[1])
        })
    }, e.prototype.process = function () {
        var t, e = this.$scrollElement.scrollTop() + this.options.offset, o = this.getScrollHeight(),
            i = this.options.offset + o - this.$scrollElement.height(), n = this.offsets, s = this.targets,
            a = this.activeTarget;
        if (this.scrollHeight != o && this.refresh(), e >= i) return a != (t = s[s.length - 1]) && this.activate(t);
        if (a && e < n[0]) return this.activeTarget = null, this.clear();
        for (t = n.length; t--;) a != s[t] && e >= n[t] && (void 0 === n[t + 1] || e <= n[t + 1]) && this.activate(s[t])
    }, e.prototype.activate = function (e) {
        this.activeTarget = e, this.clear();
        var o = this.selector + '[data-target="' + e + '"],' + this.selector + '[href="' + e + '"]',
            i = t(o).parents("li").addClass("active");
        i.parent(".dropdown-menu").length && (i = i.closest("li.dropdown").addClass("active")), i.trigger("activate.bs.scrollspy")
    }, e.prototype.clear = function () {
        t(this.selector).parentsUntil(this.options.target, ".active").removeClass("active")
    };
    var i = t.fn.scrollspy;
    t.fn.scrollspy = o, t.fn.scrollspy.Constructor = e, t.fn.scrollspy.noConflict = function () {
        return t.fn.scrollspy = i, this
    }, t(window).on("load.bs.scrollspy.data-api", function () {
        t('[data-spy="scroll"]').each(function () {
            var e = t(this);
            o.call(e, e.data())
        })
    })
}(jQuery), +function (t) {
    "use strict";

    function e() {
        var t = document.createElement("bootstrap"), e = {
            WebkitTransition: "webkitTransitionEnd",
            MozTransition: "transitionend",
            OTransition: "oTransitionEnd otransitionend",
            transition: "transitionend"
        };
        for (var o in e) if (void 0 !== t.style[o]) return {end: e[o]};
        return !1
    }

    t.fn.emulateTransitionEnd = function (e) {
        var o = !1, i = this;
        t(this).one("bsTransitionEnd", function () {
            o = !0
        });
        var n = function () {
            o || t(i).trigger(t.support.transition.end)
        };
        return setTimeout(n, e), this
    }, t(function () {
        t.support.transition = e(), t.support.transition && (t.event.special.bsTransitionEnd = {
            bindType: t.support.transition.end,
            delegateType: t.support.transition.end,
            handle: function (e) {
                return t(e.target).is(this) ? e.handleObj.handler.apply(this, arguments) : void 0
            }
        })
    })
}(jQuery);