/**
 * Created by Evgenia on 5/24/2014.
 */

var client_id = "d949653473474f228b456e888250adfe";
var client_secret = "38cb172583c84cf1b7d5ebb7a3be990b";
var redirect_uri = "https://evgeniagolubyeva.github.io/research";
var redirect_uri2 = "https://evgeniagolubyeva.github.io/research";
var apiName = "OpenApiBP";

jQuery(function ($) {
    $("#login").click(function () {
        var href = "http://sandboxapi.ihealthlabs.com/OpenApiV2/OAuthv2/userauthorization/" +
            "?client_id=" + client_id +
            "&response_type=code&redirect_uri=" + redirect_uri +
            "&APIName=" + apiName;
        console.log(href);
        window.location = href;
    });
});

function afterloaded() {
    var location = window.location;
    var href = location.href;
    var code = href.match(/code=([^]+)/)[1];

    var url = "http://sandboxapi.ihealthlabs.com/OpenApiV2/OAuthv2/userauthorization/" +
        "?client_id=" + client_id +
        "&client_secret=" + client_secret +
        "&grant_type=authorization_code" +
        "&redirect_uri=" + redirect_uri2 +
        "&code=" + code;

    var xmlHttp = new XDomainRequest();
    xmlHttp.open("GET", url, false);
    xmlHttp.setRequestHeader("Access-Control-Allow-Origin", "*");
    xmlHttp.send(null);
    console.log(xmlHttp.responseText);
}