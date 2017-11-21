
"use strict";function setOfCachedUrls(e){return e.keys().then(function(e){return e.map(function(e){return e.url})}).then(function(e){return new Set(e)})}var precacheConfig=[["/index.html","9bbed0f3c410ba663aec2f3d53316ff4"],["/static/css/main.9f8924e3.css","2f72b7fb5cf70a6fff6276c38ba6f4d9"],["/static/js/main.4dbdb735.js","e536c648eb7c9971643a9455b7ab131a"],["/static/media/BackgroundSpace.ffb167de.jpg","ffb167de5a6b2324291f2dbd34e4cde8"],["/static/media/background.68f5ccb5.jpg","68f5ccb5eb05ef5263884c49d417e947"],["/static/media/backgroundCemetery2.d24ec36d.jpg","d24ec36dc9d288560efe61edce1f1dbe"],["/static/media/backgroundCemetery3.9bff1615.jpg","9bff1615d23543f24c3c3bf94c6146f0"],["/static/media/backgroundLogin.569b46d2.jpg","569b46d284170df111361c28a20e6cf7"],["/static/media/cardback.9d3db534.jpg","9d3db53435ed41bcdab335bb962aaa0b"],["/static/media/catpaw2.fabaeff1.png","fabaeff1d21fff9dd4c566893a1d1670"],["/static/media/chatGuerrier.5a5b0f9d.jpg","5a5b0f9dc1c728dc5f2e78b1b7308222"],["/static/media/chatZorro.11f66cd1.jpg","11f66cd168f9db3a0167b2184d092f30"],["/static/media/flyingCat.ac2cc04f.gif","ac2cc04f94da5f170e90f1b87f99dbcc"],["/static/media/glyphicons-halflings-regular.448c34a5.woff2","448c34a56d699c29117adc64c43affeb"],["/static/media/glyphicons-halflings-regular.89889688.svg","89889688147bd7575d6327160d64e760"],["/static/media/glyphicons-halflings-regular.e18bbf61.ttf","e18bbf611f2a2e43afc071aa2f4e1512"],["/static/media/glyphicons-halflings-regular.f4769f9b.eot","f4769f9bdb7466be65088239c12046d1"],["/static/media/glyphicons-halflings-regular.fa277232.woff","fa2772327f55d8198301fdb8bcfc8158"],["/static/media/heart.f2e5d0a4.png","f2e5d0a4ccc3e7b586f8a7a8071e2dab"],["/static/media/loading.adf86d2a.gif","adf86d2ae5ab91f9e5e95a0e0c1c55f8"],["/static/media/metal.59269676.jpg","59269676c18025338858cc93c610b4f0"],["/static/media/radar.4c43fd34.gif","4c43fd34865a53a42d115750ceba68b0"],["/static/media/woodenBackground1.764f4199.jpg","764f4199059f41cc6470206e4fd61dd1"],["/static/media/woodenBackground2.ee4d570c.jpg","ee4d570c5c0f0c146f7dec1d0be7d7a7"]],cacheName="sw-precache-v3-sw-precache-webpack-plugin-"+(self.registration?self.registration.scope:""),ignoreUrlParametersMatching=[/^utm_/],addDirectoryIndex=function(e,a){var t=new URL(e);return"/"===t.pathname.slice(-1)&&(t.pathname+=a),t.toString()},cleanResponse=function(e){return e.redirected?("body"in e?Promise.resolve(e.body):e.blob()).then(function(a){return new Response(a,{headers:e.headers,status:e.status,statusText:e.statusText})}):Promise.resolve(e)},createCacheKey=function(e,a,t,c){var n=new URL(e);return c&&n.pathname.match(c)||(n.search+=(n.search?"&":"")+encodeURIComponent(a)+"="+encodeURIComponent(t)),n.toString()},isPathWhitelisted=function(e,a){if(0===e.length)return!0;var t=new URL(a).pathname;return e.some(function(e){return t.match(e)})},stripIgnoredUrlParameters=function(e,a){var t=new URL(e);return t.hash="",t.search=t.search.slice(1).split("&").map(function(e){return e.split("=")}).filter(function(e){return a.every(function(a){return!a.test(e[0])})}).map(function(e){return e.join("=")}).join("&"),t.toString()},hashParamName="_sw-precache",urlsToCacheKeys=new Map(precacheConfig.map(function(e){var a=e[0],t=e[1],c=new URL(a,self.location),n=createCacheKey(c,hashParamName,t,/\.\w{8}\./);return[c.toString(),n]}));self.addEventListener("install",function(e){e.waitUntil(caches.open(cacheName).then(function(e){return setOfCachedUrls(e).then(function(a){return Promise.all(Array.from(urlsToCacheKeys.values()).map(function(t){if(!a.has(t)){var c=new Request(t,{credentials:"same-origin"});return fetch(c).then(function(a){if(!a.ok)throw new Error("Request for "+t+" returned a response with status "+a.status);return cleanResponse(a).then(function(a){return e.put(t,a)})})}}))})}).then(function(){return self.skipWaiting()}))}),self.addEventListener("activate",function(e){var a=new Set(urlsToCacheKeys.values());e.waitUntil(caches.open(cacheName).then(function(e){return e.keys().then(function(t){return Promise.all(t.map(function(t){if(!a.has(t.url))return e.delete(t)}))})}).then(function(){return self.clients.claim()}))}),self.addEventListener("fetch",function(e){if("GET"===e.request.method){var a,t=stripIgnoredUrlParameters(e.request.url,ignoreUrlParametersMatching);(a=urlsToCacheKeys.has(t))||(t=addDirectoryIndex(t,"index.html"),a=urlsToCacheKeys.has(t));!a&&"navigate"===e.request.mode&&isPathWhitelisted(["^(?!\\/__).*"],e.request.url)&&(t=new URL("/index.html",self.location).toString(),a=urlsToCacheKeys.has(t)),a&&e.respondWith(caches.open(cacheName).then(function(e){return e.match(urlsToCacheKeys.get(t)).then(function(e){if(e)return e;throw Error("The cached response that was expected is missing.")})}).catch(function(a){return console.warn('Couldn\'t serve response for "%s" from cache: %O',e.request.url,a),fetch(e.request)}))}});

