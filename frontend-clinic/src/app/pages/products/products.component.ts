import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ProductService, Product } from '../../services/product.service';
import { CategoryService, Category } from '../../services/category.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent implements OnInit {
  products: Product[] = [];
  categories: Category[] = [];
  productForm: FormGroup;
  editingId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private categoryService: CategoryService,
    private router: Router
  ) {
    this.productForm = this.fb.group({
      code: ['', Validators.required],
      name: ['', Validators.required],
      description: [''],
      price: [0, [Validators.required, Validators.min(0.01)]],
      stock: [0, [Validators.required, Validators.min(0)]],
      categoryIds: [[], Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadProducts();
    this.loadCategories();
  }

  loadProducts(): void {
    this.productService.getAll().subscribe(products => this.products = products);
  }

  loadCategories(): void {
    this.categoryService.getAll().subscribe(cats => this.categories = cats);
  }

  saveProduct(): void {
    const product = this.productForm.value;
    if (this.editingId) {
      this.productService.update(this.editingId, product).subscribe(() => {
        this.loadProducts();
        this.resetForm();
      });
    } else {
      this.productService.create(product).subscribe(() => {
        this.loadProducts();
        this.resetForm();
      });
    }
  }

  editProduct(product: Product): void {
    this.editingId = product.id!;
    this.productForm.patchValue(product);
  }

  deleteProduct(id: number): void {
    if (confirm('¿Eliminar producto?')) {
      this.productService.delete(id).subscribe(() => this.loadProducts());
    }
  }

  resetForm(): void {
    this.editingId = null;
    this.productForm.reset({ price: 0, stock: 0, categoryIds: [] });
  }

  goBack(): void {
    this.router.navigate(['/dashboard']);
  }
}
