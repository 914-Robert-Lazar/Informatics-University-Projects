let currentField = 1

$(function() {
    $("#newForm").hide();
    
    $("#newFormButton").on("click", function() {

        $("#mainDiv").css({position: 'fixed'});
        $("#newDiv").css('background-color', 'grey');
        $("#newDiv").css('opacity', '0.9');
        $("#newForm").show();
        
    });

    $("#appendButton").on("click", function() {
        let concatenatedString = $("#input1").val() + $("#input2").val() + $("#input3").val() + $("#input4").val();
        $(`#text${currentField}`).val(concatenatedString);

        if (currentField == 5) {
            currentField = 1;
        }
        else {
            currentField++;
        }

        $("#input1").val("")
        $("#input2").val("")
        $("#input3").val("")
        $("#input4").val("")
        changeFormDisabledState(false);
        $("#mainDiv").css({position: 'relative'});
        $("#newDiv").css('background-color', 'white');
        $("#newDiv").css('opacity', '1');
        $("#newForm").hide();
    })
});
        
function changeFormDisabledState(state) {
    $("#text1").prop("disabled", state);
    $("#text2").prop("disabled", state);
    $("#text3").prop("disabled", state);
    $("#text4").prop("disabled", state);
    $("#text5").prop("disabled", state);
    $("#newFormButton").prop("disabled", state);
}