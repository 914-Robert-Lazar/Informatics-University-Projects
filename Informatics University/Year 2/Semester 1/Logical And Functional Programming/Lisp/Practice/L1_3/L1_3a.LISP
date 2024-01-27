(defun insertAtom (l a n)
  (cond
   ((null l) nil)
   ((and (equal (mod n 2) 0) (not (equal n 0))) (append (list a (car l)) (insertAtom (cdr l) a (+ n 1))))
   (t (cons (car l) (insertAtom (cdr l) a (+ n 1))))
   )
)