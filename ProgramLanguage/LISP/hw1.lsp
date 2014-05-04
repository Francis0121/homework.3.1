(defun last-item( lst )
	( cond
		( ( null (cdr lst) ) ( car lst ) )
		( t ( last-item ( cdr lst ) ) )
	)
)

(defun remove-1st( atm lst )
	(cond 
		( ( not ( equal atm (car lst) ) ) ( cons (car lst) ( remove-1st atm (cdr lst) ) ) )
		( t (cdr lst) )
	)
)

(defun append2 (x y)
	(cond 
		( (null (cdr x) ) (cons (car x) y) )
		( t (cons (car x) (append2 (cdr x) y) ) )
	)
)

(defun merge2 (x y)
	(cond
		( ( null x ) y )
		( ( null y ) x )
		(  
			(  <= (car x) (car y)  )   
			( cons (car x)  
				(  if (null (cdr x)) y (merge2 (cdr x) y ) ) 
			)
		)
		(  
			t
			( cons (car y) 
				( if  (null (cdr y)) x (merge2 x (cdr y) ) ) 
			)
		)
	)	
)
