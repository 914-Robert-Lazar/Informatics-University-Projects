
$(function () {
    $.getJSON("getAllTypes.php", function(data) {
        $.each(data, function(i, item) {
            $("#typeSelect").append($("<option>", {
                value: item,
                text: item
            }))
            console.log(item);
        })
    })

    $("#getForm").on("submit", function(e) {
        e.preventDefault();
        console.log($("#typeSelect").val())
        $.get("showRecipesByType.php", {type : $("#typeSelect").val()}, 
            function(data, status) {
                
                $("#Recipes").html(data);
        });
    });
});