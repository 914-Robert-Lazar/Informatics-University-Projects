import { useState } from "react";
import "./Home.css";
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

function validateForm(name, type, level) {
    if (name === "") {
        alert("The name of the exercise should not be empty.");
        return false;
    }
    if (type === "") {
        alert("You should first choose the type of exercise.");
        return false;
    }
    if (level < 1 || !Number.isInteger(level)) {
        alert("The level should be a positive integer number.");
        return false;
    }

    return true;
}

function ViewExercise({exercise, setDisplayMode}) {
    function handleClick() {
        setDisplayMode("NORMAL");
    }

    return (
        <div className="view">
            <p>Name: {exercise.name}</p>
            <p>Type: {exercise.type}</p>
            <p>Level: {exercise.level}</p>
            <button onClick={handleClick}>Back To Menu</button>
        </div>
    )
}

function EditExercise({setDisplayMode, selectedRow, exercises, setExercises, setTable, getExercises}) {
    const [exercise, setExercise] = useState(Exercise("", "", 0))
    const exerciseToChange = exercises.find((exercise) => exercise.id === selectedRow);
    function handleCancelClick() {
        setDisplayMode("NORMAL");
    }

    const handleChange = (event) => {
        const {name, value} = event.target;
        setExercise((prevFormData) => ({...prevFormData, [name]: value}))
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        exercise.level = Number(exercise.level);
        if (!validateForm(exercise.name, exercise.type, exercise.level)) {
            return;
        }
        
        for (let i = 0; i < exercises.length; ++i) {
            if (exercises[i].id == selectedRow) {
                exercises[i] = exercise;
            }
        }
        setExercises(exercises);
        setTable(getExercises(exercises));
        setDisplayMode("NORMAL");
    }

    return (
        <div className="updateForm">
            <p>Edit selected exercise</p>
            <form onSubmit={handleSubmit}>
                <label htmlFor="addName">Name: </label>
                <input id="addName" type="text" name="name" value={exercise.name} defaultValue={exerciseToChange.name} onChange={handleChange}></input><br/>
                <label htmlFor="addType">Type: </label>
                <select id="addType" name="type" value={exercise.type} defaultValue={exerciseToChange.type} onChange={handleChange}>
                    <option value="push">Push</option>
                    <option value="pull">Pull</option>
                    <option value="leg">Leg</option>
                </select><br/>
                <label htmlFor="addLevel">Level: </label>
                <input type="number" id="addLevel" name="level" value={exercise.level} onChange={handleChange}></input>
                <input type="button" id="formButton" value="Cancel" onClick={handleCancelClick}></input>
                <input type="submit" id="formButton" value="Edit"></input>
            </form>
        </div>
  )
}

function DeleteDialog({deleteDialogOpen, setDeleteDialogOpen, selectedRow, exercises, setExercises, setTable, getExercises}) {
    function handleClose() {
        setDeleteDialogOpen(false);
    }

    function handleConfirmedDelete() {
        exercises = exercises.filter((exercise) => exercise.id != selectedRow);
        setExercises(exercises);
        setTable(getExercises(exercises));
        setDeleteDialogOpen(false);
    }
    return (
        <div>
            <Dialog open={deleteDialogOpen} onClose={handleClose}>
                <DialogTitle id="alert-dialog-title">
                    {"Confirm delete"}
                </DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        Are you sure you want to delete this item?
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <button onClick={handleClose}>Cancel</button>
                    <button onClick={handleConfirmedDelete} autoFocus>Yes</button>
                </DialogActions>
            </Dialog>
        </div>
    )
}

function AddExercise({exercises, setExercises, setTable, setDisplayMode: setDisplayMode, getExercises}) {
    const [exercise, setExercise] = useState(Exercise("", "", 0))

    const handleChange = (event) => {
        const {name, value} = event.target;
        setExercise((prevFormData) => ({...prevFormData, [name]: value}))
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        exercise.level = Number(exercise.level);
        if (!validateForm(exercise.name, exercise.type, exercise.level)) {
            return;
        }

        exercises.push(exercise);
        setExercises(exercises);
        setTable(getExercises(exercises));
        setDisplayMode("NORMAL");
    }

    function handleCancelClick() {
        setDisplayMode("NORMAL");
    }
    return (
        <div className="addForm">
            <p>Add Exercise:</p>
            <form onSubmit={handleSubmit}>
                <label htmlFor="addName">Name: </label>
                <input id="addName" name="name" type="text" value={exercise.name} onChange={handleChange}></input><br/>
                <label htmlFor="addType">Type: </label>
                <select id="addType" name="type" value={exercise.type} onChange={handleChange}>
                    <option value="push">Push</option>
                    <option value="pull">Pull</option>
                    <option value="leg">Leg</option>
                </select><br/>
                <label htmlFor="addLevel">Level: </label>
                <input type="number" id="addLevel" name="level" value={exercise.level} onChange={handleChange}></input>
                <input type="button" id="formButton" value="Cancel" onClick={handleCancelClick}></input>
                <input type="submit" id="formButton" value="Add"></input>
            </form>
        </div>
  );
}

function ExerciseTable({exerciseTable}) {
    return (
        <div>
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Type</th>
                        <th>Level</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {exerciseTable}
                </tbody>
            </table>
        </div>
    );
}

let count = 0;
function Exercise(name, type, level) {
    return {id: count++, name: name, type: type, level: level}
}

function WorkoutExercises({setDisplayMode: setDisplayMode, exerciseTable}) {
    function handleAddClick() {
        setDisplayMode("ADD");
    }
    return (
        <div>
            <h2>Workout Exercises</h2>
            <ExerciseTable exerciseTable={exerciseTable}/>
            <div className="addButton">
                <button onClick={handleAddClick}>Add Exercise</button>
            </div>
        </div>
    );
}

function Workout() {
    const [displayMode, setDisplayMode] = useState("NORMAL");
    const [exercises, setExercises] = useState([
        Exercise("Push-up", "push", 2),
        Exercise("Pull-up", "pull", 3),
        Exercise("One leg squat", "leg", 4)
    ])

    const [exerciseTable, setTable] = useState(createExerciseTable(exercises, setExercises));
    const [selectedRow, setSelectedRow] = useState(-1);
    const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);

    function handleEditClick(id) {
        setSelectedRow(id);
        setDisplayMode("EDIT");
    }

    function handleDeleteClick(id) {
        setSelectedRow(id);
        setDeleteDialogOpen(true);
    }

    function handleViewClick(id) {
        setSelectedRow(id);
        setDisplayMode("VIEW");
    }

    function createExerciseTable(exercises) {
        return exercises.map(function(exercise) { return (
            <tr key={exercise.id}>
                <td>{exercise.name}</td>
                <td>{exercise.type}</td>
                <td>{exercise.level}</td>
                <td>
                    <button onClick={() => handleEditClick(exercise.id)}>Edit</button>
                    <button onClick={() => handleDeleteClick(exercise.id)}>Delete</button>
                    <button onClick={() => handleViewClick(exercise.id)}>View</button>
                </td>
            </tr>
        )});
    }

    if (displayMode === "ADD") {
        return (
            <div>
                <AddExercise exercises={exercises} setExercises={setExercises} setTable={setTable} 
                setDisplayMode={setDisplayMode} getExercises={createExerciseTable}/>
            </div>
        )
    }
    else if (displayMode === "EDIT") {
        return (
            <div>
                <EditExercise setDisplayMode={setDisplayMode} selectedRow={selectedRow} setExercises={setExercises}
                exercises={exercises} setTable={setTable} getExercises={createExerciseTable}/>
            </div>
        )
    }
    else if (displayMode === "VIEW") {
        return (
            <div>
                <ViewExercise exercise={exercises.find((exercise) => exercise.id == selectedRow)} setDisplayMode={setDisplayMode}/>
            </div>
        )
    }
    else {
        return (
            <div>
                <WorkoutExercises setDisplayMode={setDisplayMode} exerciseTable={exerciseTable}/>
                <DeleteDialog deleteDialogOpen={deleteDialogOpen} setDeleteDialogOpen={setDeleteDialogOpen}
                selectedRow={selectedRow} exercises={exercises} setExercises={setExercises} setTable={setTable} getExercises={createExerciseTable}/>
            </div>
        )
    }
}

export default Workout;

