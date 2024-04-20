isShown = false


$("#button").on("click",(function() {
    if (!isShown) {
        $("#toDisplay").hide();
        isShown = true;
    }
    else {
        $("#toDisplay").show();
        isShown = false;
    }
}));
