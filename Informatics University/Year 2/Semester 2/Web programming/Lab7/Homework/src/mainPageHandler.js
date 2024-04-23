
$(function() {
    $.get("showAllRecipes.php", function(data) {
        $("#Recipes").html(data);

        $(".edit").on("click", function() {
            console.log("xd");
            console.log($("#" + $(this).data("id")));
            localStorage.setItem('selectedRow', $("#" + $(this).data("id")).attr("id"));
            window.location.href= "http://localhost/Web%20Programming/Lab7/Homework/editPage.html";
        })

        $(".delete").on("click", function () {
            if (confirm("Are you sure you want to delete this recipe?")) {
                $.post("deleteRecipe.php", {id: parseInt($("#" + $(this).data("id")).attr("id").charAt(3))}, function(data, status) {
                    if (status == "success") {
                        alert("Recipe deleted successfully!");
                        $.get("showAllRecipes.php", function(data) {
                            $("#Recipes").html(data);
                        })
                    }
                    else {
                        alert("Something wrong");
                    }
                })
            }
        })
    })

})


