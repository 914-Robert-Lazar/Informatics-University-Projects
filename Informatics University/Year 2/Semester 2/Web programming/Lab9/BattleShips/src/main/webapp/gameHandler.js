function generateGrid(divId, cellName, gridString) {
    let tableHtml = "<table>";
    gridString = JSON.parse(gridString);
    for (let row = 0; row < 8; ++row) {
        tableHtml += "<tr>";
        for (let column = 0; column < 8; ++column) {
            if (gridString.grid[row][column] === "S") {
                tableHtml += `<td class="selectedOwnCell" id="${row}-${column}"></td>`;
            }
            else if (gridString.grid[row][column] === "H") {
                tableHtml += `<td class="hitCell" id="${row}-${column}"></td>`;
            }
            else if (gridString.grid[row][column] === "M") {
                tableHtml += `<td class="missedCell" id="${row}-${column}"></td>`;
            }
            else {
                tableHtml += `<td class=${cellName} id="${row}-${column}"></td>`;
            }
        }
        tableHtml += "</tr>";
    }
    tableHtml += "</table>";
    document.getElementById(divId).innerHTML = tableHtml;
}



$(function () {
    let placeCount = 0;
    let prevPlace = {row: "", column: ""}
    let mapPlaceCountToInput = {0: "firstShipPos1", 1: "firstShipPos2", 2: "secondShipPos1", 3: "secondShipPos2"};

    function getShipLength(place1Row, place1Column, place2Row, place2Column) {
        if (place1Column === place2Column) {
            return  Math.abs(parseInt(place1Row) - parseInt(place2Row)) + 1;
        }
        else {
            return  Math.abs(parseInt(place1Column) - parseInt(place2Column)) + 1;
        }
    }

    $(".ownCell").on("click", function (event) {
        if ($("#status").val() !== "ship-placement") {
            return;
        }
        let placement = event.target.id;
        if (placeCount < 4) {
            if (placeCount % 2 === 1) {
                if (placement[0] !== prevPlace.row && placement[2] !== prevPlace.column) {
                    alert("Wrong cell for the other end of the ship");
                    return;
                }
                if (placeCount === 1 && getShipLength(prevPlace.row, prevPlace.column, placement[0], placement[2]) !== 5) {
                    alert("You should place down a 5 cell long ship.");
                    return;
                }
                if (placeCount === 3 && getShipLength(prevPlace.row, prevPlace.column, placement[0], placement[2]) !== 4) {
                    alert("You should place down a 4 cell long ship.");
                    return;
                }

                if (placement[0] === prevPlace.row) {
                    if (placement[2] > prevPlace.column) {
                        for (let parseColumn = parseInt(prevPlace.column) + 1; parseColumn < parseInt(placement[2]); ++parseColumn) {
                            console.log(`#${prevPlace.row}-${parseColumn}`);
                            $(`#${prevPlace.row}-${parseColumn}`).addClass("selectedOwnCell").removeClass("ownCell");
                        }
                    }
                    else {
                        for (let parseColumn = parseInt(placement[2]) + 1; parseColumn < parseInt(prevPlace.column); ++parseColumn) {
                            console.log(`#${prevPlace.row}-${parseColumn}`);
                            $(`#${prevPlace.row}-${parseColumn}`).addClass("selectedOwnCell").removeClass("ownCell");
                        }
                    }
                }
                else {
                    if (placement[0] > prevPlace.row) {
                        for (let parseRow = parseInt(prevPlace.row) + 1; parseRow < parseInt(placement[0]); ++parseRow) {
                            console.log(`#${parseRow}-${prevPlace.column}`);
                            $(`#${parseRow}-${prevPlace.column}`).addClass("selectedOwnCell").removeClass("ownCell");
                        }
                    }
                    else {
                        for (let parseRow = parseInt(placement[0]) + 1; parseRow < parseInt(prevPlace.row); ++parseRow) {
                            console.log(`#${parseRow}-${prevPlace.column}`);
                            $(`#${parseRow}-${prevPlace.column}`).addClass("selectedOwnCell").removeClass("ownCell");
                        }
                    }
                }
            }
            $(`#${mapPlaceCountToInput[placeCount]}`).val(placement);
            $(this).addClass("selectedOwnCell").removeClass("ownCell");
            placeCount++;

            prevPlace.row = placement[0];
            prevPlace.column = placement[2];
            if (placeCount === 4) {
                $("#shipForm").submit();
            }
        }
    });

    $(".opponentCell").on("click", function (event) {
        if ($("#ownTurn").val()) {
            $("#bombedShipPos").val(event.target.id);
            $("#bombForm").submit();
        }
    });

    /*console.log($("#player_id").val());
    console.log($("#turn_player").val());*/
    console.log($("#status").val());
    if ($("#player_id").val() !== $("#turn_player").val() && $("#status").val() !== "lost") {
        console.log("Hello");
        let interval = setInterval(checkBombStatus, 1000);
    }


});
function checkBombStatus() {
    fetch('CheckBombedServlet')
        .then(response => response.json())
        .then(data => {
            console.log(data.status);
            if (data.status === 'switched' || data.status === 'lost') {

                window.location.href = 'game.jsp';
            }
        })
        .catch(error => console.error('Error:', error));
}

