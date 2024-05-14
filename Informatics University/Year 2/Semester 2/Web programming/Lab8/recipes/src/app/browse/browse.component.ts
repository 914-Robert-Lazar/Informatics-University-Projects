import { Component, OnInit } from '@angular/core';
import { GenericService } from '../generic.service';
import { Router } from '@angular/router';
import { NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Recipe } from '../recipe';

@Component({
  selector: 'app-browse',
  standalone: true,
  imports: [NgFor, FormsModule, NgIf],
  templateUrl: './browse.component.html',
  styleUrl: './browse.component.css'
})
export class BrowseComponent implements OnInit{

  types: string[] = [];
  prevFilter: string = "";
  selectedValue: string = "";
  prevSelectedValue: string = "";
  recipes: Recipe[] = [];
  isShown: boolean = false;
  
  constructor(private genericService: GenericService, private router: Router) { }
  
  ngOnInit(): void {
    this.prevFilter = "None";
    this.getTypes();
  }
  getTypes() {
    this.genericService.fetchTypes().subscribe(types => {this.types = types;
      console.log(this.types);
      this.selectedValue = this.types[0];
    });
  }

  selectChange(newValue: string) {
    this.selectedValue = newValue;
  }

  getRecipesWithType() {
    this.genericService.fetchRecipesWithGivenType(this.selectedValue).subscribe(recipes => this.recipes = recipes);
    if (this.prevSelectedValue != "") {
      this.prevFilter = this.prevSelectedValue;
    }
    this.prevSelectedValue = this.selectedValue;
    this.isShown = true;
  }

  backToMain() {
    this.router.navigate(["/"]);
  }
}
