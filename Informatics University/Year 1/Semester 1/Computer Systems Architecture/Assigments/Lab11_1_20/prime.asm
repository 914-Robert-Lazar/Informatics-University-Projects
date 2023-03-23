bits 32

extern printf
import printf msvcrt.dll
segment data use32 class==data
    littleMessage db "%d", 0

segment code use32 public code

global prime

prime:
    mov eax, [esp + 4]
    cmp eax, 2
    je isPrime
    cmp eax, 2
    jl isNotPrime

    push eax
    pop ax
    pop dx
    
    mov ebx, 2
    
    isItPrime:
        
        push dx
        push ax
        div bx
        
        cmp dx, 0
        je isNotPrime
        
        inc ebx
        pop ax
        pop dx
    cmp bx, ax
    jl isItPrime
    
    isPrime:
        push dword eax
        push dword littleMessage
        call [printf]
        add esp, 4 * 2
    isNotPrime:
    ret 4
