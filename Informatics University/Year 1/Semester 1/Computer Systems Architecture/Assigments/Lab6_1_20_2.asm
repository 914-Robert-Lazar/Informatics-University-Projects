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
    s db 'kecske cic kalak kalap e rutturruttur'
    lenS equ $ - s
    d db 0
    it equ 0
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ecx, lenS
        mov esi, s 
        mov edi, s
        mov eax, 0
        forS:
            cmp [edi], byte ' '
            jne notSpace
                push esi
                push edi
                dec edi
                palindrom:
                    mov dl, [esi]
                    cmp dl, [edi]
                    jne notSame
                    inc esi
                    dec edi
                cmp esi, edi
                jb palindrom
                notSame:
                cmp esi, edi
                jb palindromConfirmed
                    mov [d + eax], byte 1
                    jmp skip
                palindromConfirmed:
                    mov [d + eax], byte 0
                skip:
                inc eax
                pop edi
                pop esi
                mov esi, edi
                inc esi
            notSpace:
            inc edi
        loop forS
        dec edi
        palindrom2:
            mov dl, [esi]
            cmp dl, [edi]
            jne notSame2
            inc esi
            dec edi
        cmp esi, edi
        jb palindrom2
        notSame2:
        cmp esi, edi
        jb palindromConfirmed2
            mov [d + eax], byte 1
            jmp skip2
        palindromConfirmed2:
            mov [d + eax], byte 0
        skip2:
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
