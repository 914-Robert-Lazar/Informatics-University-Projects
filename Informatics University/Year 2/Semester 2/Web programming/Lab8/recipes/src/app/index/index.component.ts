import { Component, OnInit } from '@angular/core';
import { Recipe } from '../recipe';
import { GenericService } from '../generic.service';
import { Router } from '@angular/router';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-index',
  standalone: true,
  imports: [NgFor],
  templateUrl: './index.component.html',
  styleUrl: './index.component.css'
})
export class IndexComponent implements OnInit{
  recipes: Recipe[] = [];
  
  constructor(private genericService: GenericService, private router: Router) { }
  
  ngOnInit(): void {
    console.log("ngOnInit.");
    this.getRecipes();
  }
  
  getRecipes() {
    this.genericService.fetchRecipes().subscribe(recipes => this.recipes = recipes);
  }
  
  toBrowsingPage() {
    this.router.navigate(["/browse"]);
  }
  
  toAddPage() {
    this.router.navigate(["/add"]);
  }
  deleteClick(id: number) {
    if (confirm("Are you sure you want to delete this recipe?")) {
      this.genericService.deleteRecipe(id).subscribe();
      this.getRecipes();
    }
  }

  editClick(selectedRow: Recipe) {
    localStorage.setItem("selectedRow", JSON.stringify(selectedRow));
    this.router.navigate(["/edit"]);
  }
}
