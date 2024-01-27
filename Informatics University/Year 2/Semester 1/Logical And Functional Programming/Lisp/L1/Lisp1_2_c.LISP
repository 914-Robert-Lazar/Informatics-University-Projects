;c) Write a function to sort a linear list without keeping the double values.

(defun findMinPos(l m pos currPos)
  (cond
   ((null l) pos)
   ((< (car l) m) (progn 
                    (setq pos currPos)
                    (setq m (car l))
                    (findMinPos (cdr l) m pos (+ currPos 1))
                    )
    )
   (t (findMinPos (cdr l) m pos (+ currPos 1)))
   )
)

(defun sortWithSwap(l)
  (cond 
   ((null l) nil)
   (t (let ((pos (findMinPos l 100000 -1 0)))
     (rotatef (nth 0 l) (nth pos l))
     (cons (car l) (sortWithSwap (cdr l)))))
   )
)

(defun removeDuplicate(l prev)
  (cond
   ((null l) nil)
   ((/= prev (car l)) (cons (car l) (removeDuplicate (cdr l) (car l))))
   (t (removeDuplicate (cdr l) (car l)))
   )
)

(defun sortWithoutDuplicate(l)
  (removeDuplicate (sortWithSwap l) 100000)
)
    