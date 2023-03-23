def create_student(id, name, grade):
    return {"id": id,
            "name": name,
            "grade": grade}

def get_student_id(student):
    return student["id"]

def get_student_name(student):
    return student["name"]

def get_student_grade(student):
    return student["grade"]

def set_student_id(student, id):
     student["id"] = id

def set_student_name(student, name):
    student["name"] = name

def set_student_grade(student, grade):
    student["grade"] = grade

