(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-15bd7d62"],{"20f6":function(t,n,a){},"3b59":function(t,n,a){"use strict";a("cb2c")},"5f33":function(t,n,a){"use strict";a.r(n);var i=function(){var t=this,n=t.$createElement,a=t._self._c||n;return a("div",[a("v-container",{style:"xs"===t.breakpointName||"sm"===t.breakpointName?"padding-top: 75px":"",attrs:{id:"users",fluid:"",tag:"section"}},[a("v-card-text",{staticStyle:{"max-width":"750px",margin:"15px auto",background:"#fff"}},[a("img",{staticStyle:{width:"100px","border-radius":"10px",position:"absolute"},attrs:{src:"/images/qrcode.png",alt:""}}),a("div",{staticClass:"pt-5"},[a("h3",{staticClass:"mb-3",staticStyle:{"text-align":"center"}},[t._v("UBND PHƯỜNG THẠCH BÀN")]),a("p",{staticStyle:{"text-align":"center"}},[t._v("Xác nhận: Ông /bà:")]),a("p",{staticStyle:{"text-align":"center","font-weight":"bold"}},[t._v("Nguyễn Văn A")]),a("div",{staticClass:"px-3"},[a("p",{staticStyle:{"margin-top":"50px"}},[t._v("Địa chỉ nơi ở/ cư trú: ")]),a("p",{staticStyle:{"font-weight":"bold","padding-left":"50px"}},[t._v("1xx Nguyễn Văn Cừ - Phường Ngọc Lâm - Long Biên - Hà Nội")]),a("p",[t._v("Địa điểm làm việc: ")]),a("p",{staticStyle:{"font-weight":"bold","padding-left":"50px"}},[t._v("Khu CN Sài đồng B - Thạch Bàn - Long Biên – HN")]),a("p",[t._v("Số CCCD/CMTND: "),a("span",{staticStyle:{"font-weight":"bold"}},[t._v("123456789")])]),a("p",[t._v("Đơn vị/cty: "),a("span",{staticStyle:{"font-weight":"bold"}},[t._v("Cty TNHH và TMDV ABCDXEZ ")])]),a("p",[t._v("Thời gian làm việc: ")]),a("p",{staticStyle:{"font-weight":"bold","padding-left":"50px"}},[t._v("Từ thứ 2 đến thứ 7")]),a("p",{staticStyle:{"font-weight":"bold","padding-left":"50px"}},[t._v("Từ 08h đến 17h00")]),a("p",[t._v("Thời hạn sử dụng: ")]),a("p",{staticStyle:{"font-weight":"bold","padding-left":"50px"}},[t._v("Đến 15/9/2021")]),a("p",{staticStyle:{"font-weight":"bold","padding-left":"50px"}},[t._v("(hoặc: Cho đến khi có thông báo mới)")])])])])],1)],1)},e=[],r={name:"GiayDiDuong",components:{},props:["qruid"],data:function(){return{}},created:function(){},watch:{},computed:{breakpointName:function(){return this.$store.getters.getBreakpointName}},methods:{}},o=r,c=(a("3b59"),a("2877")),s=a("6544"),d=a.n(s),p=a("99d9"),l=a("a523"),f=Object(c["a"])(o,i,e,!1,null,"449ac294",null);n["default"]=f.exports;d()(f,{VCardText:p["b"],VContainer:l["a"]})},a523:function(t,n,a){"use strict";a("4de4"),a("b64b"),a("2ca0"),a("99af"),a("20f6"),a("4b85");var i=a("e8f2"),e=a("d9f7");n["a"]=Object(i["a"])("container").extend({name:"v-container",functional:!0,props:{id:String,tag:{type:String,default:"div"},fluid:{type:Boolean,default:!1}},render:function(t,n){var a,i=n.props,r=n.data,o=n.children,c=r.attrs;return c&&(r.attrs={},a=Object.keys(c).filter((function(t){if("slot"===t)return!1;var n=c[t];return t.startsWith("data-")?(r.attrs[t]=n,!1):n||"string"===typeof n}))),i.id&&(r.domProps=r.domProps||{},r.domProps.id=i.id),t(i.tag,Object(e["a"])(r,{staticClass:"container",class:Array({"container--fluid":i.fluid}).concat(a||[])}),o)}})},cb2c:function(t,n,a){},e8f2:function(t,n,a){"use strict";a.d(n,"a",(function(){return e}));a("498a"),a("99af"),a("4de4"),a("b64b"),a("2ca0"),a("a15b");var i=a("2b0e");function e(t){return i["a"].extend({name:"v-".concat(t),functional:!0,props:{id:String,tag:{type:String,default:"div"}},render:function(n,a){var i=a.props,e=a.data,r=a.children;e.staticClass="".concat(t," ").concat(e.staticClass||"").trim();var o=e.attrs;if(o){e.attrs={};var c=Object.keys(o).filter((function(t){if("slot"===t)return!1;var n=o[t];return t.startsWith("data-")?(e.attrs[t]=n,!1):n||"string"===typeof n}));c.length&&(e.staticClass+=" ".concat(c.join(" ")))}return i.id&&(e.domProps=e.domProps||{},e.domProps.id=i.id),n(i.tag,e,r)}})}}}]);