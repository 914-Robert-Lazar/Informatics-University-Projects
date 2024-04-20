


function createComponent() {
    const selected = $("#selectType").find(":selected").val();
    if (selected == "table") {
        let columnN = 1, rowN = 1;
        const addColumn = $("<button></button>").text("+C").attr("id", "addColumn")
        const addRow = $("<button></button>").text("+R").attr("id", "addRow")
        $("#container").append(addColumn)
        $("#container").append(addRow)

        $("#addColumn").on("click", function() {

        })
    }
}

function createColumn(rowN) {
    for (let i = 0; i < rowN; ++i) {
        const cell = $("<input></input>")
        $("#container").append(cell)
    }
    
}

function createRow() {

}