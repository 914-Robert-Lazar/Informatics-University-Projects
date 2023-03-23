bits 32

global _doubleInReverse

segment data public date use32
    paramListAddress dd 0
    listSize dd 0
    resultListAdress dd 0

segment code public code use32

_doubleInReverse:
    push ebp
    mov ebp, esp
    
    mov eax, [ebp + 8]
    mov [paramListAddress], eax
    mov eax, [ebp + 12]
    mov [listSize], eax
    mov eax, [ebp + 16]
    mov [resultListAdress], eax
    
    mov esi, [paramListAddress]
    mov edi, 0
    std
    mov ecx, [listSize]
    mov dx, 2
    jecxz ending
    forLoop:
        lodsd
        
        mul dx
        push dx
        push ax
        pop eax
        
        mov [resultListAdress + edi], eax
        add edi, 4
    loop forLoop
    ending:
    
    mov esp, ebp
    pop ebp
    ret
    