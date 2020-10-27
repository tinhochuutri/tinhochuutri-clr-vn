(function (a) {
    ProductDetailGallery = function (b) {
        this.autoplayInterval = null;
        this.thumbs = a("#thumbs");
        this.iconback = a("#icon-back");
        this.iconnext = a("#icon-next");
        this.autoplay = a("#autoplay");
        this.photoactive = a("#divPhotoActive > div:eq(0)");
        $gallery = this;
        this.selectNextImage = function () {
            if (this.thumbs.children("li.current").length <= 0) {
                if (this.thumbs.children("li").length > 1) {
                    this.selectImg(this.thumbs.children("li:eq(1)"))
                }
            } else {
                var c = this.thumbs.children("li.current").index();
                if (c < this.thumbs.children("li").length - 1) {
                    this.selectImg(this.thumbs.children("li:eq(" + (c + 1) + ")"))
                } else {
                    this.selectImg(this.thumbs.children("li:eq(0)"))
                }
            }
        };
        this.selectBackImage = function () {
            var d = this.thumbs.children("li").length - 1;
            if (this.thumbs.children("li.current").length <= 0) {
                if (this.thumbs.children("li").length > 1) {
                    this.selectImg(this.thumbs.children("li:eq(1)"))
                }
            } else {
                var c = this.thumbs.children("li.current").index();
                if (c > 0) {
                    this.selectImg(this.thumbs.children("li:eq(" + (c - 1) + ")"))
                } else {
                    this.selectImg(this.thumbs.children("li:eq(" + d + ")"))
                }
            }
        };
        this.selectImg = function (e) {
            if (this.photoactive == null) {
                return
            }
            this.thumbs.children("li").removeClass("current");
            a(e).addClass("current");
            var c = a(e).children("img");
            var f = a(c).attr("src");
            var d = new RegExp("thumb[0-9]{1,3}x[0-9]{1,3}.");
            f = f.replace(d, "thumb745x510.");
            var d = new RegExp("/resize/[0-9]{1,3}x[0-9]{1,3}/");
            f = f.replace(d, "/resize/745x510/");
            if (this.photoactive.children("img:eq(0)").css("display") == "inline" || this.photoactive.children("img:eq(0)").css("display") == "") {
                this.photoactive.children("img:eq(1)").attr("src", f);
                this.photoactive.children("img:eq(0)").fadeOut("500", function () {
                    $gallery.photoactive.children("img:eq(1)").fadeIn("500")
                })
            } else {
                this.photoactive.children("img:eq(0)").attr("src", f);
                this.photoactive.children("img:eq(1)").fadeOut("500", function () {
                    $gallery.photoactive.children("img:eq(0)").fadeIn("500")
                })
            }
        };
        if (this.thumbs.children("li").length <= 1) {
            this.iconback.hide();
            this.iconnext.hide();
            this.autoplay.hide()
        } else {
            this.autoplay.bind("click", this, function (c) {
                if (a(this).attr("ac") == "auto") {
                    a(this).children("span").html("Xem tự động");
                    a(this).attr("ac", "manual");
                    c.data.photoactive.css("cursor", "pointer");
                    window.clearInterval(c.data.autoplayInterval);
                    c.data.autoplayInterval = null
                } else {
                    a(this).children("span").html("Tắt xem tự động");
                    a(this).attr("ac", "auto");
                    c.data.photoactive.css("cursor", "default");
                    c.data.selectNextImage();
                    c.data.autoplayInterval = c.data.autoplayInterval = window.setInterval(function () {
                        c.data.selectNextImage()
                    }, 3000)
                }
            });
            this.photoactive.bind("click", this, function (c) {
                if (c.data.autoplayInterval == null) {
                    c.data.selectNextImage()
                }
            });
            a("#thumbs").children("li").bind("click", this, function (c) {
                c.data.selectImg(this)
            });
            this.iconback.bind("click", this, function (c) {
                c.data.selectBackImage(this)
            });
            this.iconnext.bind("click", this, function (c) {
                c.data.selectNextImage(this)
            })
        }
    };
    a.fn.ProductDetailGallery = ProductDetailGallery
}(jQuery));
(function (a) {
    ProductDetailMap = function (d, e, b, c) {
        $this = this;
        this.infoWindow = null, this.map = null;
        this.marker = null;
        this.markerUtilities = new Array();
        this.circle = null;
        this.searchVar = {};
        this.dataUtilities = new Array();
        this.tooltip = null;
        this.InitMap = function (g, h, f) {
            a("#maputility").html('<iframe width="100%" height="100%" frameborder= "0" style= "border:0" src="https://www.google.com/maps/embed/v1/place?q=' + g + "," + h + "&key=" + googleMapLink + '&zoom=14" allowfullscreen></iframe>')
        };
        this.SearchAction = function () {
            this.ClearPoint();
            this.searchVar.radius = a(".utilityradius > label > input[type=radio]:checked").val();
            var f = "";
            a(".utilitylist > label > input[type=checkbox]:checked").each(function () {
                if (f.length > 0) {
                    f += ","
                }
                f += a(this).val()
            });
            if (f.length == 0) {
                return
            }
            this.searchVar.types = f;
            this.searchVar.lat = this.marker.position.lat();
            this.searchVar.lon = this.marker.position.lng();
            this.searchVar.m = "pddetail";
            this.searchVar.v = new Date().getTime();
            a(".MapProductDetail").block({
                message: '<img width="40" src="https://file4.batdongsan.com.vn/images/Product/Maps/map-loading.gif">',
                css: {border: "1px solid #ccc", padding: "none", width: "40px", height: "40px"}
            });
            a.ajax({
                url: mapHostUrl + "/api/u_sync",
                data: this.searchVar,
                dataType: "json",
                type: getAjaxMethod(),
                success: function (g, i, h) {
                    a(".MapProductDetail").unblock();
                    if (g != undefined && g != null) {
                        $this.ShowPoint(eval(a.dde(g.data)))
                    }
                },
                error: function (h, i, g) {
                    a(".MapProductDetail").unblock()
                },
                complete: function () {
                }
            })
        };
        this.ClearPoint = function () {
            if (this.circle != null) {
                this.circle.setMap(null)
            }
            for (var f = 0; f < this.markerUtilities.length; f++) {
                this.markerUtilities[f].setMap(null)
            }
            this.markerUtilities = new Array();
            a(".utilityResultList").html("");
            a(".utilityResultHeader").html("")
        };
        this.ShowPoint = function (f) {
            this.infoWindow.close();
            this.dataUtilities = new Array();
            var j = parseInt(this.searchVar.radius);
            var k = j + " m";
            if (j > 1000) {
                k = (j / 1000) + " km"
            }
            if (f != undefined && f != null && f.length > 0) {
                if (j < 1000) {
                    a(".utilityResultHeader").html("Các tiện ích bản đồ trong bán kính " + k)
                } else {
                    a(".utilityResultHeader").html("Các tiện ích bản đồ trong bán kính " + k)
                }
                if (this.circle == null) {
                    this.circle = new google.maps.Circle({center: this.marker.position, radius: j, fillOpacity: 0.4})
                } else {
                    this.circle.setOptions({center: this.marker.position, radius: j})
                }
                this.circle.setMap(this.map);
                this.dataUtilities = $this.formatUtilities(f, this.marker.position, j);
                a(".utilitylist label .uti-total").remove();
                a.each(a(".utilitylist input:checked"), function () {
                    var m = parseInt(a(this).val());
                    var i = $this.getTotalUtility($this.dataUtilities, m);
                    if (a(this).parent().find(".uti-total").length > 0) {
                        a(this).parent().find(".uti-total").html("(" + i + ")")
                    } else {
                        a(this).parent().append(' <span class="uti-total">(' + i + ")</span>")
                    }
                });
                for (var h = 0; h < this.dataUtilities.length; h++) {
                    var l = this.dataUtilities[h];
                    var g = "";
                    g += '<div class="infowindow-util-preview">';
                    g += '<b class="infowindow-util-preview-title">' + l.name + "</b>";
                    if (l.address != null && l.address.length > 0) {
                        g += "<span>" + l.address + "</span><br/>"
                    }
                    g += "<b>Khoảng cách: </b>" + l.distance + "m";
                    g += "</div>";
                    this.markerUtilities.push(new google.maps.Marker({
                        position: new google.maps.LatLng(l.lat, l.lon),
                        map: this.map,
                        tooltip: g,
                        icon: {
                            url: "https://file4.batdongsan.com.vn/images/Product/Maps/utility" + this.dataUtilities[h].typeid + ".png",
                            size: new google.maps.Size(32, 37)
                        },
                        zIndex: 9
                    }));
                    this.markerUtilities[this.markerUtilities.length - 1].id = this.dataUtilities[h].id;
                    this.markerUtilities[this.markerUtilities.length - 1].addListener("click", function () {
                        $this.ShowUtilityWindow(this.id)
                    });
                    this.markerUtilities[this.markerUtilities.length - 1].addListener("mouseover", function (i) {
                        var m = i.latLng;
                        if (m == null) {
                            m = this.getPosition()
                        }
                        $this.tooltip.addTip(this);
                        $this.tooltip.getPos2(m)
                    });
                    this.markerUtilities[this.markerUtilities.length - 1].addListener("mouseout", function (i) {
                        $this.tooltip.removeTip()
                    })
                }
            } else {
                a(".utilityResultHeader").html("Không có tiện ích nào trong bán kính " + k)
            }
            this.ShowListResult()
        };
        this.ShowListResult = function () {
            if (this.dataUtilities != undefined && this.dataUtilities != null && this.dataUtilities.length > 0) {
                a(".utilitylist > label > input[type=checkbox]:checked").each(function () {
                    var j = parseInt(a(this).val());
                    var g = "";
                    for (var h = 0; h < $this.dataUtilities.length; h++) {
                        var k = $this.dataUtilities[h];
                        if (k.typeid == j) {
                            g += "<tr>";
                            g += '<td class="col40per">' + k.name + "</td>";
                            g += '<td class="col40per">' + k.address + "</td>";
                            var f = parseInt(google.maps.geometry.spherical.computeDistanceBetween(new google.maps.LatLng(k.lat, k.lon), $this.marker.position));
                            g += '<td class="col20per">' + f + " m</td>";
                            g += "</tr>"
                        }
                    }
                    if (g.length > 0) {
                        g = '<div class="resulthead"><table><tr><th class="col40per">' + a(this).parent().text() + '</th><th  class="col40per">Địa chỉ</th><th  class="col20per">Khoảng cách</th></tr></table></div><div class="resultbody"><table>' + g + "</table></div>";
                        a(".utilityResultList").append(g)
                    }
                })
            }
        };
        this.ShowUtilityWindow = function (h) {
            this.infoWindow.close();
            for (var g = 0; g < this.dataUtilities.length; g++) {
                if (this.dataUtilities[g].id == h) {
                    for (var k = 0; k < this.markerUtilities.length; k++) {
                        if (this.markerUtilities[k].id == h) {
                            var m = this.dataUtilities[g];
                            var f = "";
                            f += '<div class="infowindow-util">';
                            f += '<b class="infowindow-util-title">' + m.name + "</b>";
                            if (m.image != null && m.image.length > 0) {
                                f += '<div class="infowindow-util-ava">';
                                f += '<a class="fancybox" rel="gallery1" href="' + m.image[0] + '"><img src="' + m.image[0] + '" alt="" /></a>';
                                for (var l = 1; l < m.image.length; l++) {
                                    f += '<a class="fancybox" rel="gallery1" href="' + m.image[l] + '"></a>'
                                }
                                f += "</div>"
                            }
                            if (m.address != null && m.address.length > 0) {
                                f += "<span>" + m.address + "</span><br/>"
                            }
                            if (m.website != null && m.website.length > 0) {
                                f += "<span>" + m.website + "</span><br/>"
                            }
                            if (m.email != null && m.email.length > 0) {
                                f += "<span>" + m.email + "</span><br/>"
                            }
                            if (m.phone != null && m.phone.length > 0) {
                                f += "<span>" + m.phone + "</span><br/>"
                            }
                            f += "<b>Khoảng cách:</b> " + parseInt(google.maps.geometry.spherical.computeDistanceBetween(this.markerUtilities[k].position, this.marker.position)) + "m";
                            f += "</div>";
                            this.infoWindow.setContent(f);
                            this.infoWindow.open(this.map, this.markerUtilities[k], 37);
                            google.maps.event.clearListeners(this.infoWindow, "closeclick");
                            a(".fancybox").fancybox({openEffect: "none", closeEffect: "none"})
                        }
                    }
                }
            }
        };
        this.getTotalUtility = function (f, j) {
            var h = 0;
            for (var g = 0; g < f.length; g++) {
                if (f[g].typeid == j) {
                    h++
                }
            }
            return h
        };
        this.formatUtilities = function (g, f, k) {
            if (g == null || g.length == 0) {
                return []
            }
            var l = [];
            for (var j = 0; j < g.length; j++) {
                var h = parseInt(google.maps.geometry.spherical.computeDistanceBetween(new google.maps.LatLng(g[j].lat, g[j].lon), f));
                if (h <= k) {
                    g[j].distance = h;
                    l.push(g[j])
                }
            }
            return l
        };
        this.InitMap(d, e, b);
        if (c) {
            a(".utilitylist").find("input").each(function (f, g) {
                if (a(this).val() == 4 || a(this).val() == 1 || a(this).val() == 2) {
                    a(this).prop("checked", true)
                } else {
                    a(this).prop("checked", false)
                }
            });
            this.SearchAction()
        }
    };
    a.fn.ProductDetailMap = ProductDetailMap
}(jQuery));
var isLoadProductMap = false;
var curTabIndex = 0;

function showPhoto(a) {
    if (curTabIndex == 0) {
        return
    }
    curTabIndex = 0;
    $(".detail-more-info a.active").removeClass("active");
    $(a).addClass("active");
    $("#googleMap").hide();
    $("#photo360").hide();
    $("#photoSlide").show()
}

function loadMapCallback() {
    $("#photoSlide").hide();
    $("#googleMap").show();
    if ($(".MapProductDetail").length > 0) {
        var a = $("#photoSlide").length > 0 || $("#photo360").length > 0;
        $(".MapProductDetail").ProductDetailMap($("#hdLat").val(), $("#hdLong").val(), $("#hdAddress").val(), a)
    }
}

function showMap(a) {
    if (curTabIndex == 1) {
        return
    }
    curTabIndex = 1;
    $(".detail-more-info a.active").removeClass("active");
    $(a).addClass("active");
    if (!isLoadProductMap) {
        isLoadProductMap = true;
        loadMapCallback();
        $("#photo360").hide();
        return
    }
    $("#photoSlide").hide();
    $("#photo360").hide();
    $("#googleMap").show()
}

function showMapInfo(a) {
    $(a).hide();
    showMap()
}

function show360(a) {
    writeScript("https://content.batdongsan.com.vn/Scripts/jquery.scrollto.js", function () {
        writeScript("https://content.batdongsan.com.vn/Scripts/pannellum/pannellum.js", function () {
            writeScript("https://content.batdongsan.com.vn/Scripts/pannellum/Img360.js", function () {
                writeScript("https://content.batdongsan.com.vn/Scripts/pannellum/Swiper-2.7.6/idangerous.swiper.min.js", function () {
                    if (curTabIndex == 2) {
                        return
                    }
                    curTabIndex = 2;
                    $(".detail-more-info a.active").removeClass("active");
                    $(a).addClass("active");
                    $("#photoSlide").hide();
                    $("#googleMap").hide();
                    $("#photo360").show();
                    $("#photo360 .thumbs360 li:first").trigger("click");
                    if ($("#photo360 .thumbs360 li").length == 1) {
                        $("#autoplay360").hide()
                    }
                })
            })
        })
    })
}

Array.prototype.getUnique = function () {
    var e = {}, b = [];
    for (var c = 0, d = this.length; c < d; ++c) {
        if (e.hasOwnProperty(this[c])) {
            continue
        }
        b.push(this[c]);
        e[this[c]] = 1
    }
    return b
};

function urlify(c) {
    var f = c;
    try {
        if (c.match(/<\/a>/) != null) {
            return c
        }
        var i = /((https:\/\/|http:\/\/|www)[^\s><]+)/ig;
        var d = c.match(i);
        if (d != null && d.length > 0) {
            var h = d[0];
            var g = h;
            if (h.toLowerCase().startsWith("https://") == false && h.toLowerCase().startsWith("http://") == false) {
                h = "http://" + h
            }
            c = c.replace(new RegExp(g.replace(/\//ig, "\\/").replace(/\?/ig, "\\?").replace(/\+/ig, "\\+")), '<a rel="nofollow" target="_blank" href="' + h + '">' + g + "</a>")
        }
        var a = /[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}/g;
        var e = c.match(a);
        if (e) {
            e = e.getUnique();
            $.each(e, function (j, k) {
                c = c.replace(k, "<a href='mailto:" + k + "'>" + k + "</a>")
            })
        }
    } catch (b) {
        return f
    }
    return c
}

$(document).ready(function () {
    $("#TopContentLeft").parent().remove();
    if ($("#photoSlide").length > 0) {
        $("#photoSlide").ProductDetailGallery()
    } else {
        if ($("#photo360").length > 0) {
            show360($(".view-360"))
        } else {
            if ($("#liMap").length > 0) {
                showMap($("#liMap"))
            }
        }
    }
    $(".openFancy").fancybox({
        width: 500,
        height: 500,
        autoScale: true,
        transitionIn: "none",
        transitionOut: "none",
        type: "iframe",
        scrolling: "no"
    });
    $(".pm-title h1").html($(".pm-title h1").html().replace(/đặc khu/ig, ""));
    $(".pm-desc").html($(".pm-desc").html().replace(/đặc khu/ig, ""));
    $(".pm-bottom div").html($(".pm-bottom div").html().replace(/đặc khu/ig, ""));
    if ($(".pm-desc").length > 0 && ($(".pm-desc").html().match(/<\/a>/) == null || $(".pm-desc").html().match(/<\/a>/).length == 0)) {
        $(".pm-desc").html(urlify($(".pm-desc").html()))
    }
    $(".btn-feedback").click(function (a) {
        if ($("#popup_feedback").length == 0) {
            var b = '<div class="pl-popup fb-popup" id="popup_feedback">';
            b += '<div class="pl-popup-title fb-popup">';
            b += '<span class="fb-popup">Tin rao có các thông tin không đúng:</span>';
            b += '<div id="pl-popup-close" class="fb-popup">X</div></div>';
            b += '<div class="pl-popup-tab fb-popup">';
            b += '<div class="button current fb-popup" rel="info">Tin rao</div>';
            b += '<div class="button fb-popup" rel="map">Bản đồ</div>';
            b += '<div class="pl-popup-tab-content tab-info fb-popup">';
            b += '<label for="info-1" class="fb-popup">';
            b += '<input type="checkbox" id="info-1" class="fb-popup" value="Địa chỉ của bất động sản" />Địa chỉ của bất động sản</label>';
            b += '<label for="info-2" class="fb-popup">';
            b += '<input type="checkbox" id="info-2" class="fb-popup" value=" Các thông tin về đặc điểm: giá, diện tích, mô tả ...." />Các thông tin về đặc điểm: giá, diện tích, mô tả ....</label>';
            b += '<label for="info-3" class="fb-popup">';
            b += '<input type="checkbox" id="info-3" class="fb-popup" value="Ảnh" />Ảnh</label>';
            b += '<label for="info-4" class="fb-popup">';
            b += '<input type="checkbox" id="info-4" class="fb-popup" value="Trùng với tin rao khác" />Trùng với tin rao khác</label>';
            b += '<label for="info-5" class="fb-popup">';
            b += '<input type="checkbox" id="info-5" class="fb-popup" value="Không liên lạc được" />Không liên lạc được</label>';
            b += '<label for="info-6" class="fb-popup">';
            b += '<input type="checkbox" id="info-7" class="fb-popup" value="Tin không có thật" />Tin không có thật</label>';
            b += '<label for="info-7" class="fb-popup">';
            b += '<input type="checkbox" id="info-8" class="fb-popup" value="Bất động sản đã bán" />Bất động sản đã bán</label>';
            b += '<label class="fb-popup">Phản hồi khác:</label>';
            b += '<textarea cols="20" rows="5" id="info-9" class="fb-popup"></textarea></div>';
            b += '<div class="pl-popup-tab-content tab-map fb-popup">';
            b += '<label for="map-1" class="fb-popup">';
            b += '<input type="checkbox" id="map-1" class="fb-popup" value="Tốc độ load chậm" />Tốc độ load chậm</label>';
            b += '<label for="map-2" class="fb-popup">';
            b += '<input type="checkbox" id="map-2" class="fb-popup" value="Vị trí bất động sản chưa chính xác" />Vị trí bất động sản chưa chính xác</label>';
            b += '<label for="map-3" class="fb-popup">';
            b += '<input type="checkbox" id="map-3" class="fb-popup" value="Vị trí tiện ích chưa chính xác" />Vị trí tiện ích chưa chính xác</label>';
            b += '<label for="map-4" class="fb-popup">';
            b += '<input type="checkbox" id="map-4" class="fb-popup" value="Bản đồ lỗi" />Bản đồ lỗi</label>';
            b += '<label class="fb-popup">Phản hồi khác:</label>';
            b += '<textarea cols="20" rows="5" id="map-5" class="fb-popup"></textarea></div>';
            b += '<div class="pl-popup-captcha fb-popup">';
            b += '<span style="float:left;" class="fb-popup">Mã bảo vệ: </span>';
            b += '<input type="text" id="popup-captcha" class="fb-popup" style="width: 130px; float:left;" />';
            b += '<input type="hidden" id="popup-etag" />';
            b += '<img class="pl-popup-imgcaptcha fb-popup" id="pl-popup-img-captcha" src="" style="width:80px; height:20px;" />';
            b += "<img ";
            b += 'class="pl-popup-imgrefresh fb-popup" ';
            b += "onmouseover=\"this.style.cursor='pointer'\" ";
            b += "onclick=\"javascript:refreshPublicCaptcha('pl-popup-img-captcha','popup-etag');\" ";
            b += 'title="Đổi mã an toàn" ';
            b += 'alt="renew capcha" ';
            b += 'src="https://file4.batdongsan.com.vn/images/Home/images/icon-reload.png">';
            b += "</div>";
            b += '<div class="pl-popup-message"></div>';
            b += '<div class="btn-green fb-popup">';
            b += '<span class="fb-popup"><span class="fb-popup">';
            b += '<a class="btn-sendfeedback fb-popup" rel="nofollow" href="javascript:void(0)">Gửi</a>';
            b += "</span></span>";
            b += "</div>";
            b += "</div>";
            b += "</div>";
            $(this).parent().parent().append(b);
            $(".pl-popup-tab > div.button").click(function () {
                $(".pl-popup-tab > div.button").removeClass("current");
                $(this).addClass("current");
                var c = $(this).attr("rel");
                $(".pl-popup-tab-content").hide();
                $(".tab-" + c).show()
            });
            $("#pl-popup-close").click(function () {
                $(".pl-popup").css("display", "none");
                $("body").unbind("click")
            });
            $(".btn-sendfeedback").click(function () {
                $(".pl-popup-message").html("");
                var g = 0;
                var e = "";
                var h = "";
                if ($(".pl-popup-tab div.button[rel=info]").hasClass("current")) {
                    g = 1;
                    h = "info"
                } else {
                    if ($(".pl-popup-tab div.button[rel=stats]").hasClass("current")) {
                        g = 2;
                        h = "stats"
                    } else {
                        if ($(".pl-popup-tab div.button[rel=map]").hasClass("current")) {
                            g = 3;
                            h = "map"
                        }
                    }
                }
                $(".tab-" + h + " input[type=checkbox]:checked").each(function () {
                    e = e + $(this).val() + ". "
                });
                var f = Trim($(".tab-" + h + " textarea").val());
                var c = $("#popup-captcha").val();
                if (c.length == 0) {
                    alert("Bạn cần nhập mã bảo vệ");
                    return
                }
                if (e.length == 0 && f.length == 0) {
                    alert("Bạn cần chọn/nhập phản hồi");
                    return
                }
                $(".pl-popup-message").html("Đang gửi phản hồi");
                try {
                    ga("send", "event", "ProductDetail", "Feedback", "Gửi phản hồi tin rao")
                } catch (d) {
                }
                $.post("/HandlerWeb/FeedbackServiceHandler.ashx", {
                    type: "productdetail",
                    fbType: g,
                    fbText: e + ". " + f,
                    captcha: c,
                    lnk: window.location.href,
                    etag: $("#popup-etag").val()
                }, function (i) {
                    $(".pl-popup-message").html("");
                    if (i.success == false) {
                        alert(i.msg)
                    } else {
                        $(".pl-popup").css("display", "none");
                        $("body").unbind("click");
                        refreshCaptcha("pl-popup-img-captcha");
                        $("#popup-captcha").val("");
                        $(".pl-popup-tab-content label input").removeAttr("checked");
                        $(".pl-popup-tab-content textarea").val("");
                        alert("Cảm ơn bạn đã gửi phản hồi đến chúng tôi!")
                    }
                }, "json")
            })
        }
        refreshPublicCaptcha("pl-popup-img-captcha", "popup-etag");
        $(".pl-popup").css("display", "block");
        a.preventDefault();
        a.stopPropagation();
        $("body").bind("click", this, function (c) {
            if ($(c.target).hasClass("fb-popup")) {
                return
            }
            $(".pl-popup").css("display", "none");
            $("body").unbind("click")
        })
    });
    $(".btn-email").click(function () {
        try {
            ga("send", "event", "ProductDetail", "SendEmailToFriend", "Gửi tin rao cho bạn bè")
        } catch (a) {
        }
        $.fancybox("/SendMail.aspx?returnurl=https://batdongsan.com.vn" + window.location.pathname + escape("?utm_source=email&utm_campaign=ShareProduct&utm_medium=send_to_friend"), {
            width: 450,
            height: 450,
            autoScale: true,
            transitionIn: "none",
            transitionOut: "none",
            type: "iframe",
            scrolling: "no",
            type: "iframe"
        })
    })
});