(defun f(l1 l2)
  ((lambda (v)
     (append v
             (cond
              ((null l1) (cdr l2))
              (t (list v (car l2)))
              )
             )
     )
   (f (car l1) l2)
   )
)


(defun f2(l1 l2)
  (append (f (car l1) l2)
          (cond
           ((null l1) (cdr l2))
           (t (list (f (car l1 ) l2) (car l2)))
           )
          )
)

(defun g(l)
  (mapcon #'list l)
)

(defun remove3s (l)
  (cond
   ((and (numberp l) (equal (mod l 3) 0)) nil)
   ((atom l) (list l))
   (t (list (mapcan #'remove3s l)))
   )
)

(defun remove3 (l)
  (car (remove3s l))
)
   

  