$(document).ready(function () {
    init();
    search();
    reset();

});

function init() {
    $('#trailers').empty();
    $.get("/bin/movies?type=top", function (data, status) {
        if (data.length > 0) {
            data.forEach(e => createCard(e));
        }
    });
}

function reset() {
    $('.resetButton').click(function () {
        $('#top-bar').html('Movie Trailer App - Top Movies');
        init();
        $('.searchText').val('');
    });

}

function search() {
    $('.searchButton').click(function () {
        searchOnText();
    });

    $('.searchText').keypress(function (key) {
        var keycode = event.keyCode || event.which;
        if (keycode == '13') {
            searchOnText();
        }

    });
}

function searchOnText() {
    $('#top-bar').html('Movie Trailer App - Search Movies');
    let movieName = $('.searchText').val();

    $.get("/bin/movies?type=search&movieName=" + movieName, function (data, status) {
        if (data.length > 0) {
            $('#trailers').empty();
            data.forEach(e => createCard(e));
        }
    });
}

function createCard(e) {
    let cardData = "";
    cardData += "<a href=\"" + e.videoUrl + "\" target=\"_blank\">";
    cardData += "<coral-card fixedwidth assetwidth=\"200\" assetheight=\"200\" class=\"trailerCard\">";
    cardData += "<coral-card-asset><img src=\"" + e.image + "\"></coral-card-asset>";
    cardData += "<coral-card-overlay>";
    cardData += "<div class=\"u-coral-padding\">";
    cardData += "<coral-card-context>Year -" + e.year + "</coral-card-context>";
    cardData += "<coral-card-context>Title -" + e.title + " </coral-card-context>";
    cardData += "</div></coral-card-overlay></coral-card>";
    cardData += "</a>"

    $('#trailers').append(cardData);
}

