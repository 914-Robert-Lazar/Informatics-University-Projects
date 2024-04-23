$(function () {
    $("#addForm").on("submit", function(e) {
        e.preventDefault();
        if (validateForm()) {
            $.post("addRecipe.php", {
                author: $("#authorInput").val(),
                name: $("#nameInput").val(),
                type: $("#typeInput").val(),
                description: $("#descriptionInput").val()
            }, function(data, status) {
                if (status == "success") {
                    alert("Recipe added successfully!");
                    window.location.href= "http://localhost/Web%20Programming/Lab7/Homework/index.html";
                }
                else {
                    alert("Something went wrong");
                }
            })
        }
    });

    $("#cancelButton").on("click", function() {
        window.location.href= "http://localhost/Web%20Programming/Lab7/Homework/index.html";
    })
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

