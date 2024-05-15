import { Component } from '@angular/core';
import { GenericService } from '../generic.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-edit',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './edit.component.html',
  styleUrl: './edit.component.css'
})
export class EditComponent {
  id: number = JSON.parse(localStorage.getItem("selectedRow")!).id;
  authorInput: string = JSON.parse(localStorage.getItem("selectedRow")!).author;
  nameInput: string = JSON.parse(localStorage.getItem("selectedRow")!).name;
  typeInput: string = JSON.parse(localStorage.getItem("selectedRow")!).type;
  descriptionInput: string = JSON.parse(localStorage.getItem("selectedRow")!).description;
  
  constructor(private genericService: GenericService, private router: Router) { }
  
  
  editSubmit() {
    console.log(this.id);
    this.genericService.editRecipe(this.id, this.authorInput, this.nameInput, this.typeInput, this.descriptionInput)
    .subscribe(text => console.log(text));
    
    this.router.navigate(["/"]);
  }
  backToMenu() {
    this.router.navigate(["/"]);
  }
}
