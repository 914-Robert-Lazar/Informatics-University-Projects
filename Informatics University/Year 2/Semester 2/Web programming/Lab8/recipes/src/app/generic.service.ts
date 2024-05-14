import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, of } from 'rxjs';
import { Recipe } from './recipe';

@Injectable({
  providedIn: 'root'
})
export class GenericService {
  private backendUrl = 'http://localhost/Web%20Programming/Lab8/backend';
  httpOptions = {
    headers: new HttpHeaders({ 
        'Content-Type': 'application/json'
    })
}; 

  constructor(private http: HttpClient) { }

  fetchRecipes() : Observable<Recipe[]> {
    return this.http.get<Recipe[]>(this.backendUrl + "/showAllRecipes.php")
      .pipe(catchError(this.handleError<Recipe[]>('fetchRecipes', [])));
  }

  fetchTypes() : Observable<string[]> {
    return this.http.get<string[]>(this.backendUrl + "/getAllTypes.php")
      .pipe(catchError(this.handleError<string[]>('fetchTypes', [])));
  }

  fetchRecipesWithGivenType(type: string) : Observable<Recipe[]> {
    let params = new HttpParams();
    params = params.append('type', type);
    return this.http.get<Recipe[]>(this.backendUrl + "/showRecipesByType.php", {params: params})
      .pipe(catchError(this.handleError<Recipe[]>('fetchRecipesWithGivenType', [])));
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  } 
}
