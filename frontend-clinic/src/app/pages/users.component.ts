import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService, User } from '../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {
  users: User[] = [];
  userForm: FormGroup;
  editingId: number | null = null;

  constructor(private userService: UserService, private fb: FormBuilder, private router: Router) {
    this.userForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: [''],
      admin: [false] 
    });
  }

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers() {
    this.userService.getAll().subscribe(users => this.users = users);
  }

  saveUser() {
    const userData = this.userForm.value;
    if (this.editingId) {
      this.userService.update(this.editingId, userData).subscribe(() => {
        this.loadUsers();
        this.resetForm();
      });
    } else {
      this.userService.create(userData).subscribe(() => {
        this.loadUsers();
        this.resetForm();
      });
    }
  }

  editUser(user: User) {
    this.editingId = user.id!;
    this.userForm.patchValue({
      username: user.username,
      email: user.email,
      password: '',
      admin: user.roles?.includes('ROLE_ADMIN') ?? false
    });
  }

  deleteUser(id: number) {
    if (confirm('¿Estás seguro de eliminar este usuario?')) {
      this.userService.delete(id).subscribe(() => this.loadUsers());
    }
  }

  resetForm() {
    this.userForm.reset({ admin: false });
    this.editingId = null;
  }

  goBack(): void {
    this.router.navigate(['/dashboard']);
  }
}
