/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import {Component, Input, OnInit} from '@angular/core';
import {DataExplorerWidgetModel} from "../../../../core-model/gen/streampipes-model";
import {WidgetBaseAppearanceConfig} from "../../../models/dataview-dashboard.model";

@Component({
  selector: 'sp-data-explorer-widget-appearance-settings',
  templateUrl: './data-explorer-widget-appearance-settings.component.html',
  styleUrls: ['./data-explorer-widget-appearance-settings.component.scss'],
})
export class DataExplorerWidgetAppearanceSettingsComponent implements OnInit {

  @Input() baseAppearanceConfig: WidgetBaseAppearanceConfig;

  presetColors: Array<any> = ["#39B54A", "#1B1464", "#f44336", "#4CAF50", "#FFEB3B", "#FFFFFF", "#000000"];

  ngOnInit(): void {

  }


}