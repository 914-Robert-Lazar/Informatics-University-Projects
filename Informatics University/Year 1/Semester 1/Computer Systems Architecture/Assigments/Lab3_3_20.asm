bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    a dw 5h
    b db -16h
    e dd -2999887h
    x dq 99999999999453h
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ax, [a]
        mov dx, 2
        imul dx ;dx:ax = 2 * a 
        push dx
        push ax
        mov al, [b]
        cbw
        cwd
        mov bx, ax
        mov cx, dx ;cx:bx = b 
        pop ax
        pop dx
        sub ax, bx
        sbb dx, cx ; dx:ax = 2 * a - b 
        mov bx, ax
        mov cx, dx ;cx:bx = 2 * a - b 
        mov al, [b]
        imul byte [b] ; ax = b * b 
        push ax
        mov bx, ax
        mov dx, cx ;dx:ax = 2 * a - b
        pop bx
        idiv bx ; ax = (2 * a - b) / (b * b)
        cwde 
        add eax, [e] ;eax = (2 * a - b) / (b * b) + e 
        mov edx, eax
        mov al, [b]
        cbw
        cwde
        sub edx, eax ;edx = (2 * a - b) / (b * b) + e - b
        add edx, 8
        mov eax, edx
        cdq 
        add eax, [x]
        adc edx, [x + 4]
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
