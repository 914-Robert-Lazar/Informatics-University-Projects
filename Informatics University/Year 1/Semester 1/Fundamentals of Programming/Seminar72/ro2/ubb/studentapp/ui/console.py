import Seminar72.ro2.ubb.studentapp.domain.operations as operations


def get_command():
    line = input("Input the command you want: ")
    pos = line.find(" ")
    cmd_name = line[:pos]
    args = line[pos + 1:].split(',')
    args = [arg.strip() for arg in args]
    # for arg in args:
    #     res = arg.strip()
    return cmd_name, args


def add_student(students, id, name, grade):
    try:
        id = int(id)
    except ValueError as e:
        print("ID must be a natural number.", e)
        return
    try:
        grade = int(grade)
    except ValueError as e:
        #print("Grade must be a natural number.", e)
        raise ValueError("Grade must be a natural number.", e)
    # todo: handle exceptions

    operations.add_student(students, id, name, grade)


def print_all(students):
    print(students)


def run():
    students = []
    options = {"add": add_student, "print-all": print_all}
    while True:
        cmd, args = get_command()
        try:
            options[cmd](students, *args)
        except ValueError as e:
            print(e)
        except KeyError as e:
            print("Option not yet implemented", e)

