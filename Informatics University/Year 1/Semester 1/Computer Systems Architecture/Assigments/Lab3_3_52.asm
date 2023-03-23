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
    a dd 2999887h
    b db 16h
    c dw 5h
    x dq 99999999999453h
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov al, [b]
        mov ah, 0
        mov dx, 0 ; dx:ax = b
        div word [c] ; ax = b / c
        shl eax, 16
        shr eax, 16
        add eax, [a] ; eax = a + b / c
        sub eax, 1
        mov ebx, eax
        mov al, [b]
        add al, 2
        mov ah, 0 ;ax = b + 2
        mov cx, ax
        mov eax, ebx
        div cx ; ax = (a + b / c - 1) / (b + 2)
        shl eax, 16
        shr eax, 16
        mov edx, 0
        sub eax, [x]
        sub edx, [x + 4] ; edx:eax = (a + b / c - 1) / (b + 2) - x
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
