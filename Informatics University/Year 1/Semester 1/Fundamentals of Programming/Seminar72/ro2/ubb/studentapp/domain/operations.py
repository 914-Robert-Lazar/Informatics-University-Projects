from Seminar72.ro2.ubb.studentapp.domain.entities import create_student


def add_student(students, id, name, grade):
    students.append(create_student(id, name, grade))
    #todo: check if the id is unique
