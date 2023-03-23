bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)

;Two byte strings s1 and s2 are given. Build the byte string d such that, for every byte s2[i] in s2, d[i] contains either the position of byte s2[i] in s1, either the value of 0

segment data use32 class=data
    ; ...
    s1 db 7, 33, 55, 19, 46
    lenS1 equ $ - s1
    s2 db 46, 21, 7, 13, 27, 19, 55, 1, 33
    lenS2 equ $ - s2
    d times lenS2 db 0
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ecx, lenS2
        mov esi, 0
        forInS2:
            mov edi, s1
            mov al, [s2 + esi]
            
            mov ebx, ecx
            mov ecx, lenS1
            repnz scasb
            mov ecx, ebx
            
            sub edi, s1
            cmp edi, lenS1
            jne notAtLast
                dec edi
                cmp al, [s1 + edi]
                jne notTheLast
                    mov [d + esi], byte lenS1
                    jmp theLast
                notTheLast:
                inc edi
            notAtLast:
            mov eax, edi
            
            cmp al, lenS1
            jb found
                mov [d + esi], byte 0
                jmp notFound
            found:
            
                mov [d + esi], al
            notFound:
            theLast:
            inc esi
        loop forInS2
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
