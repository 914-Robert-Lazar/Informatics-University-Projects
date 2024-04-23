$(function () {
    console.log(localStorage.getItem("selectedRow").charAt(3));
    $.getJSON("getSelectedRecipe.php", {id: localStorage.getItem("selectedRow").charAt(3)}, function(data) {
        $("#authorInput").val(data.author);
        $("#nameInput").val(data.name);
        $("#typeInput").val(data.type);
        $("#descriptionInput").val(data.description);
    });
    
    $("#editForm").on("submit", function (e) {
        e.preventDefault();
        if (validateForm()) {
            $.post("editRecipe.php", {
                id: parseInt(localStorage.getItem("selectedRow").charAt(3)),
                author: $("#authorInput").val(),
                name: $("#nameInput").val(),
                type: $("#typeInput").val(),
                description: $("#descriptionInput").val()
            }, function(data, status) {
                // $("#result").html(status);
                // console.log(status);
                if (status == "success") {
                    alert("Recipe edited successfully!");
                    window.location.href= "http://localhost/Web%20Programming/Lab7/Homework/index.html";
                }
                else {
                    alert("Something wrong");
                }
            })
        }
    })

    $("#cancelButton").on("click", function() {
        window.location.href= "http://localhost/Web%20Programming/Lab7/Homework/index.html";
    });
});

function validateForm() {
    if ($("#authorInput").val() == "") {
        alert("The author input should not be empty!");
        return false;
    }
    if ($("#nameInput").val() == "") {
        alert("The name input should not be empty!");
        return false;
    }
    if ($("#typeInput").val() == "") {
        alert("The type input should not be empty!");
        return false;
    }
    if ($("#descriptionInput").val() == "") {
        alert("The description input should not be empty!");
        return false;
    }

    return true;
}