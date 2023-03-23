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
    A db 2, 1, 3, 3, 4, 2, 6
    lenA equ $ - A
    B db 4, 5, 7, 6, 2, 1
    lenB equ $ - B
    R db 0
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ecx, lenB
        mov esi, 0
        mov edi, lenB
        dec edi
        jecxz endFor
        forIndex:
            mov al, [B + edi]
            dec edi
            mov [R + esi], al
            inc esi
        loop forIndex
        endFor:
        mov ecx, lenA
        mov edi, 0
        jecxz endFor2
        forIndex2:
            mov al, [A + edi]
            test al, 1
            jnp skip
                mov [R + esi], al
                inc esi
            skip:
            inc edi
        loop forIndex2
        endFor2:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
