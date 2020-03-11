import { NgModule, } from '@angular/core';
import { CommonModule, } from '@angular/common';

import { CovalentLayoutModule, CovalentStepsModule, CovalentCommonModule /*, any other modules */ } from '@covalent/core';

import {
    MatListModule, MatButtonModule, MatCardModule, MatIconModule,
    MatInputModule, MatMenuModule, MatSidenavModule, MatToolbarModule,
    MatExpansionModule, MatTooltipModule, MatDialogModule, MatSelectModule, MatFormFieldModule,
    MatRadioModule, MatCheckboxModule, MatStepperModule, MatSnackBarModule, MatGridListModule,
} from '@angular/material';

import { FlexLayoutModule, } from '@angular/flex-layout';

import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {DragDropModule} from '@angular/cdk/drag-drop';

const FLEX_LAYOUT_MODULES: any[] = [
    FlexLayoutModule,
];

const ANGULAR_MODULES: any[] = [
    BrowserAnimationsModule,
    FormsModule,
    DragDropModule
];

const MATERIAL_MODULES: any[] = [
    MatListModule,
    MatButtonModule,
    MatIconModule,
    MatToolbarModule,
    MatCardModule,
    MatMenuModule,
    MatSidenavModule,
    MatInputModule,
    MatExpansionModule,
    MatTooltipModule,
    MatDialogModule,
    MatSelectModule,
    MatFormFieldModule,
    MatRadioModule,
    MatCheckboxModule,
    MatSnackBarModule,
    MatStepperModule,
    MatGridListModule
];

const COVALENT_MODULES: any[] = [
    CovalentLayoutModule,
    CovalentStepsModule,
    CovalentCommonModule,
];

@NgModule({
    imports: [
        CommonModule,
        ANGULAR_MODULES,
        MATERIAL_MODULES,
        COVALENT_MODULES,
        FLEX_LAYOUT_MODULES,
        FormsModule,
        ReactiveFormsModule
    ],
    declarations: [

    ],
    exports: [
        ANGULAR_MODULES,
        MATERIAL_MODULES,
        COVALENT_MODULES,
        FLEX_LAYOUT_MODULES,
    ]
})

export class CustomModule {
}
