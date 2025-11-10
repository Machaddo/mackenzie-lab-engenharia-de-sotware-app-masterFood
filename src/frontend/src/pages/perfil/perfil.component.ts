import { Component, OnInit, OnDestroy } from "@angular/core";
import { CommonModule, NgIf } from "@angular/common";
import { PhoneMaskDirective } from "shared/Directives/phone-mask.directive";
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
} from "@angular/forms";
import { AuthService } from "core/services/auth/auth.service";
import { Observable, Subscription } from "rxjs";
import { User } from "core/models/user.models";
import { filter, tap } from "rxjs/operators";
import { Router } from "@angular/router";

@Component({
  selector: "app-perfil",
  standalone: true,
  imports: [CommonModule, NgIf, ReactiveFormsModule, PhoneMaskDirective],
  templateUrl: "./perfil.component.html",
  styleUrl: "./perfil.component.css",
})
export class PerfilComponent implements OnInit, OnDestroy {
  currentUser$: Observable<User | null> = this.authService.currentUser$;
  private userSnapshot: User | null = null;
  private userSubscription!: Subscription;

  accountForm!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.userSubscription = this.currentUser$
      .pipe(
        filter((user): user is User => !!user),
        tap((user) => {
          this.userSnapshot = user;
          this.initializeForms(user);
        })
      )
      .subscribe();
  }
  private initializeForms(user: User): void {
    this.accountForm = this.formBuilder.group({
      name: [user.name, Validators.required],
      login: [user.login, Validators.required],
    });
  }

  ngOnDestroy(): void {
    if (this.userSubscription) {
      this.userSubscription.unsubscribe();
    }
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}