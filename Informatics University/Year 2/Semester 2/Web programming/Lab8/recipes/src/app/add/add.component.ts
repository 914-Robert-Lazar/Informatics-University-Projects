import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { GenericService } from '../generic.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './add.component.html',
  styleUrl: './add.component.css'
})
export class AddComponent {
  authorInput: string = "";
  nameInput: string = "";
  typeInput: string = "";
  descriptionInput: string = "";
  
  constructor(private genericService: GenericService, private router: Router) { }
  
  
  addSubmit() {
    this.genericService.addRecipe(this.authorInput, this.nameInput, this.typeInput, this.descriptionInput)
    .subscribe(recipe => console.log(recipe));
    
    this.router.navigate(["/"]);
  }
  backToMenu() {
    this.router.navigate(["/"]);
  }
}
