import { Directive, ElementRef, HostListener, Input } from '@angular/core';
import { NgControl } from '@angular/forms';

@Directive({
  selector: '[appPhoneMask]',
  standalone: true
})
export class PhoneMaskDirective {

  constructor(private el: ElementRef, private control: NgControl) {}

  @HostListener('input', ['$event'])
  onInputChange(event: Event) {
    const input = this.el.nativeElement as HTMLInputElement;
    let value = input.value.replace(/\D/g, ''); // Remove tudo que não é dígito

    if (value.length > 11) {
      value = value.substring(0, 11); // Limita a 11 dígitos
    }
    
    let maskedValue = '';
    
    if (value.length > 0) {
      maskedValue = '(' + value.substring(0, 2);
    }
    if (value.length > 2) {
      // Verifica se é celular (9 dígitos) ou fixo (8 dígitos)
      if (value.length <= 10) { 
        maskedValue += ') ' + value.substring(2, 6) + '-' + value.substring(6, 10);
      } else { 
        maskedValue += ') ' + value.substring(2, 7) + '-' + value.substring(7, 11);
      }
    }
    
    // Define o valor formatado no campo de input
    input.value = maskedValue;

    // Atualiza o FormControl com o valor PURO (sem máscara), para evitar problemas no backend/validação
    if (this.control.control) {
      this.control.control.setValue(value, { emitEvent: false });
    }
  }
}