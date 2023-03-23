use32

global Compute
extern ranks
extern sum

segment code use32 class=code
    Compute:
        mov esi, [esp + 4]
        mov edi, ranks
        forloop:
            lodsd
            cmp eax, 0
            je done
            
            mov dl, 0
            mov ecx, 4
            getMax:
                cmp al, dl
                jb skip
                    mov dl, al
                    mov dh, cl; dh is the current rank
                    
                skip:
                ror eax, 8
            loop getMax
            mov al, dh
            xlat
            stosb
            
            add [sum], dl
        jmp forloop
        done:
            ret
        