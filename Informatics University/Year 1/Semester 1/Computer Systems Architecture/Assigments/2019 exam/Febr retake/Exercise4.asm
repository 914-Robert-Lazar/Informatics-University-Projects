bits 32

global start

extern exit, printf
import exit msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    string dd 1234A678h, 12785634h, 1A4D3C2Bh, 0
    lens equ $ - string
    sum dd 0
    printFormat db 'The number of 1 bits in solution string is: %d'
    sol_string dw 0
    
segment code use32 class=code
    start:
        mov esi, string
        mov edi, sol_string
        
        mov ecx, lens
        jecxz done
        forLoop:
            
            inc esi
            lodsb
            
            mov ah, al
            inc esi
            lodsb
            
            stosw
            
            push ecx
                mov ecx, 8
                getSol:
                    test ax, 1
                    jz skip
                        inc dword [sum]
                    skip:
                loop getSol
            pop ecx
        loop forLoop
        done:
        
        push dword [sum]
        push dword printFormat
        call [printf]
        
        push dword 0
        call [exit]
        