(defun isMember (l e)
  (cond
   ( (and (atom l) (equal l e)) t)
   ( (atom l) nil)
   (t (some #'identity (mapcar #'(lambda (a) (isMember a e)) l) ))
  )
)
