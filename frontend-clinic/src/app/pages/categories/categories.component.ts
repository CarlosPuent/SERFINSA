import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CategoryService, Category } from '../../services/category.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-categories',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.scss']
})
export class CategoriesComponent implements OnInit {
  categoryForm: FormGroup;
  categories: Category[] = [];
  editingId: number | null = null;

  constructor(private fb: FormBuilder, private categoryService: CategoryService, private router: Router) {
    this.categoryForm = this.fb.group({
      code: ['', Validators.required],
      name: ['', Validators.required],
      description: ['']
    });
  }

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories() {
    this.categoryService.getAll().subscribe(cats => this.categories = cats);
  }

  saveCategory() {
    const data = this.categoryForm.value;
    if (this.editingId) {
      this.categoryService.update(this.editingId, data).subscribe(() => {
        this.loadCategories();
        this.resetForm();
      });
    } else {
      this.categoryService.create(data).subscribe(() => {
        this.loadCategories();
        this.resetForm();
      });
    }
  }

  editCategory(category: Category) {
    this.editingId = category.id!;
    this.categoryForm.patchValue(category);
  }

  deleteCategory(id: number) {
    if (confirm('¿Estás seguro de eliminar esta categoría?')) {
      this.categoryService.delete(id).subscribe(() => this.loadCategories());
    }
  }

  resetForm() {
    this.categoryForm.reset();
    this.editingId = null;
  }

  goBack(): void {
    this.router.navigate(['/dashboard']);
  }
}
