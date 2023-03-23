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
    S db 'a', 'A', 'b', 'B', '2', '%', 'x'
    len equ $ - S
    D db 0
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ecx, len
        mov esi, 0
        mov edi, 0
        jecxz endFor
        forIndex:
            mov al, [S + esi]
            cmp al, 'a'
            jl skip1
                cmp al, 'z'
                jg skip2
                    mov [D + edi], al
                    inc edi
                skip2:
            skip1:
            inc esi
        loop forIndex
        endFor:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
