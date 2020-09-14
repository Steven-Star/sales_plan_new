<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true">
      <el-form-item label="year" prop="year">
        <el-date-picker
          v-model="queryParams.year"
          type="year"
          value-format="yyyy"
          placeholder="input Year">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="Country" prop="country">
        <el-select v-model="queryParams.country" placeholder="请选择Country">
           <el-option
            v-for="item in options"
            :key="item.id"
            :label="item.country"
            :value="item.country">
       </el-option>
          </el-select>
      </el-form-item>
      <el-form-item label="SKU" prop="sku">
              <el-autocomplete
                        class="inline-input"
                        clearable
                        v-model="queryParams.sku"
                        :fetch-suggestions="querySearchSku"
                        placeholder="input SKU"
                        :trigger-on-focus="true" 
                        @select="handleSelect"
                        @keyup.enter.native="search"
                ></el-autocomplete>
      </el-form-item>
      <el-form-item label="Customer" prop="customerName">
                <el-autocomplete
                        class="inline-input"
                        clearable
                        v-model="queryParams.customerName"
                        :fetch-suggestions="querySearch"
                        placeholder="input Customer"
                        :trigger-on-focus="true" 
                        @select="handleSelect"
                        @keyup.enter.native="search"
                  ></el-autocomplete>
      </el-form-item>
      <el-form-item label="Model#" prop="modelName">
        <el-input
          v-model="queryParams.modelName"
          placeholder="input Model#"
          clearable
          size="small"
          style="width: 180px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">search</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">reset</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :border="true" height="750" :header-cell-style="columnStyle" :data="newTekCalendarVOList" @selection-change="handleSelectionChange">
      <el-table-column :key="Math.random()" type="selection" width="40" align="center" />
      <el-table-column fixed="left" :key="Math.random()" header-align="center" label="Model#" sortable align="center"  prop="modelName" :show-overflow-tooltip="true" width="120" />
      <el-table-column fixed="left" :key="Math.random()" header-align="center" label="Sku" sortable align="center" :show-overflow-tooltip="true"  prop="sku" width="120" />
      <el-table-column fixed="left" :key="Math.random()" header-align="center" label="Country" sortable align="center" :show-overflow-tooltip="true"  prop="country" width="120" />
      <el-table-column fixed="left" :key="Math.random()" header-align="center" label="Customer" sortable sortable align="center"  prop="customerName" :show-overflow-tooltip="true" width="120" />
       <el-table-column fixed="left" :key="Math.random()" header-align="center" label="MSRP-Country" align="center"   prop="msrpCountry" :show-overflow-tooltip="true" width="120"> 
          <template slot-scope="scope">
        <span style="margin-left: 10px">{{ scope.row.msrpCountry }}</span>
      </template>
      </el-table-column>
      <el-table-column fixed="left" :key="Math.random()" header-align="center" label="MSRP-USD" align="center"  prop="msrp" :show-overflow-tooltip="true" width="120"> 
          <template slot-scope="scope">
        <span style="margin-left: 10px">{{ scope.row.msrp }}</span>
      </template>
      </el-table-column>
      <el-table-column :key="Math.random()" header-align="center" label="YEAR" prop="year" align="center" width="80" />
      <!-- <el-table-column :key="Math.random()" header-align="center" label="Promotion Type" prop="promotionType" align="center" width="120" /> -->
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK1-Country" prop="wk1Msrp" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk1Status === -1">
            <span v-if="scope.row.wk1Msrp !== null">
              <span @click="showWk1Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk1Msrp }}</span>
              <b v-if="b011">+</b>
                      <b v-if="b012">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk1Status !== -1">
                <span v-if="scope.row.wk1Msrp !== null">
                  <span @click="showWk1Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk1Msrp }}</span>
                  <b v-if="b011">+</b>
                      <b v-if="b012">-</b>
               </span>
          </span>   
      </template>
      </el-table-column>  
          <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK1-USD" prop="wk1MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk1Status === -1">
            <span v-if="scope.row.wk1MsrpUSD !== null">
              <span @click="showWk1Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk1MsrpUSD }}</span>
                      <b v-if="b011">+</b>
                      <b v-if="b012">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk1Status !== -1">
                <span v-if="scope.row.wk1MsrpUSD !== null">
                  <span @click="showWk1Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk1MsrpUSD }}</span>
                  <b v-if="b011">+</b>
                      <b v-if="b012">-</b>
               </span>
                <span v-else-if="scope.row.wk1Msrp !== null">
                          <span @click="showWk1Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk1Msrp }}</span>
                          <b v-if="b011">+</b>
                      <b v-if="b012">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column>  

      <el-table-column :key="Math.random()" v-if="showWk1" header-align="center" :label="a011" prop="wk1Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk1Day1Status === -1">
                  <span v-if="scope.row.wk1Day1 !== null">
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk1Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk1Day1Status !== -1">
                  <span v-if="scope.row.wk1Day1 !== null">
              <span style="margin-left: 10px;">{{ scope.row.wk1Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk1Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk1Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk1" header-align="center" :label="a012" prop="wk1Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk1Day2Status === -1">
                  <span v-if="scope.row.wk1Day2 !== null">
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk1Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk1Day2Status !== -1">
                  <span v-if="scope.row.wk1Day2 !== null">
              <span  style="margin-left: 10px;">{{ scope.row.wk1Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk1Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk1Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk1" header-align="center" :label="a013" prop="wk1Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk1Day3Status === -1">
                  <span v-if="scope.row.wk1Day3 !== null">
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk1Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk1Day3Status !== -1">
                  <span v-if="scope.row.wk1Day3 !== null">
              <span  style="margin-left: 10px;">{{ scope.row.wk1Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk1Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk1Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk1" header-align="center" :label="a014" prop="wk1Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk1Day4Status === -1">
                  <span v-if="scope.row.wk1Day4 !== null">
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk1Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk1Day4Status !== -1">
                  <span v-if="scope.row.wk1Day4 !== null">
              <span  style="margin-left: 10px;">{{ scope.row.wk1Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk1Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk1Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk1" header-align="center" :label="a015" prop="wk1Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk1Day5Status === -1">
                  <span v-if="scope.row.wk1Day5 !== null">
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk1Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk1Day5Status !== -1">
                  <span v-if="scope.row.wk1Day5 !== null">
              <span style="margin-left: 10px;">{{ scope.row.wk1Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk1Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk1Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk1" header-align="center" :label="a016" prop="wk1Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk1Day6Status === -1">
                  <span v-if="scope.row.wk1Day6 !== null">
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk1Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk1Day6Status !== -1">
                  <span v-if="scope.row.wk1Day6 !== null">
              <span style="margin-left: 10px;">{{ scope.row.wk1Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk1Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk1Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk1" header-align="center" :label="a017" prop="wk1Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk1Day7Status === -1">
                  <span v-if="scope.row.wk1Day7 !== null">
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk1Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk1Day7Status !== -1">
                  <span v-if="scope.row.wk1Day7 !== null">
              <span style="margin-left: 10px;">{{ scope.row.wk1Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk1Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk1Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK2-Country" prop="wk2Msrp" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
              <span v-if="scope.row.wk2Status === -1">
                <span v-if="scope.row.wk2Msrp !== null">
                  <span @click="showWk2Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk2Msrp }}</span>
                      <b v-if="b021">+</b>
                      <b v-if="b022">-</b>
                </span>
              </span>   
              <span v-else-if="scope.row.wk2Status !== -1">
                <span v-if="scope.row.wk2Msrp !== null">
              <span @click="showWk2Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk2Msrp }}</span>
                      <b v-if="b021">+</b>
                      <b v-if="b022">-</b>
              </span>
              </span>   
          </template>
      </el-table-column>
        <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK2-USD" prop="wk2MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
              <span v-if="scope.row.wk2Status === -1">
                <span v-if="scope.row.wk2MsrpUSD !== null">
                  <span @click="showWk2Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk2MsrpUSD }}</span>
                          <b v-if="b021">+</b>
                          <b v-if="b022">-</b>
                </span>
              </span>   
                <span v-else-if="scope.row.wk2Status !== -1">
                      <span v-if="scope.row.wk2MsrpUSD !== null">
                        <span@click="showWk2Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk2MsrpUSD }}</span>
                        <b v-if="b021">+</b>
                            <b v-if="b022">-</b>
                      </span>
                              <span v-else-if="scope.row.wk2Msrp !== null">
                                <span @click="showWk2Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk2Msrp }}</span>
                                  <b v-if="b021">+</b>
                                  <b v-if="b022">-</b>
                              </span>
                </span> 
          </template>
        </el-table-column> 
            <el-table-column :key="Math.random()" v-if="showWk2" header-align="center" :label="a021" prop="wk2Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk2Day1Status === -1">
                  <span v-if="scope.row.wk2Day1 !== null">
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk2Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk2Day1Status !== -1">
                  <span v-if="scope.row.wk2Day1 !== null">
              <span style="margin-left: 10px;">{{ scope.row.wk2Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk2Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk2Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk2" header-align="center" :label="a022" prop="wk2Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk2Day2Status === -1">
                  <span v-if="scope.row.wk2Day2 !== null">
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk2Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk2Day2Status !== -1">
                  <span v-if="scope.row.wk2Day2 !== null">
              <span  style="margin-left: 10px;">{{ scope.row.wk2Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk2Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk2Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk2" header-align="center" :label="a023" prop="wk2Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk2Day3Status === -1">
                  <span v-if="scope.row.wk2Day3 !== null">
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk2Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk2Day3Status !== -1">
                  <span v-if="scope.row.wk2Day3 !== null">
              <span  style="margin-left: 10px;">{{ scope.row.wk2Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk2Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk2Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk2" header-align="center" :label="a024" prop="wk2Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk2Day4Status === -1">
                  <span v-if="scope.row.wk2Day4 !== null">
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk2Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk2Day4Status !== -1">
                  <span v-if="scope.row.wk2Day4 !== null">
              <span  style="margin-left: 10px;">{{ scope.row.wk2Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk2Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk2Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk2" header-align="center" :label="a025" prop="wk2Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk2Day5Status === -1">
                  <span v-if="scope.row.wk2Day5 !== null">
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk2Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk2Day5Status !== -1">
                  <span v-if="scope.row.wk2Day5 !== null">
              <span style="margin-left: 10px;">{{ scope.row.wk2Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk2Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk2Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk2" header-align="center" :label="a026" prop="wk2Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk2Day6Status === -1">
                  <span v-if="scope.row.wk2Day6 !== null">
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk2Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk2Day6Status !== -1">
                  <span v-if="scope.row.wk2Day6 !== null">
              <span style="margin-left: 10px;">{{ scope.row.wk2Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk2Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk2Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk2" header-align="center" :label="a027" prop="wk2Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk2Day7Status === -1">
                  <span v-if="scope.row.wk2Day7 !== null">
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk2Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk2Day7Status !== -1">
                  <span v-if="scope.row.wk2Day7 !== null">
              <span style="margin-left: 10px;">{{ scope.row.wk2Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk2Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk2Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK3-Country" prop="wk3Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk3Status === -1">
                 <span v-if="scope.row.wk3Msrp !== null">
              <span @click="showWk3Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk3Msrp }}</span>
                            <b v-if="b031">+</b>
                            <b v-if="b032">-</b>
                 </span>
              </span>   
              <span v-else-if="scope.row.wk3Status !== -1">
                <span v-if="scope.row.wk3Msrp !== null">
              <span @click="showWk3Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk3Msrp }}</span>
                            <b v-if="b031">+</b>
                            <b v-if="b032">-</b>
                </span>
              </span>   
          </template>
      </el-table-column>
        <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK3-USD" prop="wk3MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk3Status === -1">
            <span v-if="scope.row.wk3MsrpUSD !== null">
              <span @click="showWk3Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk3MsrpUSD }}</span>
               <b v-if="b031">+</b>
                            <b v-if="b032">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk3Status !== -1">
                <span v-if="scope.row.wk3MsrpUSD !== null">
                  <span @click="showWk3Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk3MsrpUSD }}</span>
                   <b v-if="b031">+</b>
                            <b v-if="b032">-</b>
               </span>
                <span v-else-if="scope.row.wk3Msrp !== null">
                          <span @click="showWk3Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk3Msrp }}</span>
                           <b v-if="b031">+</b>
                            <b v-if="b032">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk3" header-align="center" :label="a031" prop="wk3Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk3Day1Status === -1">
                  <span v-if="scope.row.wk3Day1 !== null">
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk3Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk3Day1Status !== -1">
                  <span v-if="scope.row.wk3Day1 !== null">
              <span style="margin-left: 10px;">{{ scope.row.wk3Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk3Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk3Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk3" header-align="center" :label="a032" prop="wk3Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk3Day2Status === -1">
                  <span v-if="scope.row.wk3Day2 !== null">
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk3Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk3Day2Status !== -1">
                  <span v-if="scope.row.wk3Day2 !== null">
              <span  style="margin-left: 10px;">{{ scope.row.wk3Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk3Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk3Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk3" header-align="center" :label="a033" prop="wk3Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk3Day3Status === -1">
                  <span v-if="scope.row.wk3Day3 !== null">
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk3Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk3Day3Status !== -1">
                  <span v-if="scope.row.wk3Day3 !== null">
              <span  style="margin-left: 10px;">{{ scope.row.wk3Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk3Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk3Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk3" header-align="center" :label="a034" prop="wk3Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk3Day4Status === -1">
                  <span v-if="scope.row.wk3Day4 !== null">
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk3Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk3Day4Status !== -1">
                  <span v-if="scope.row.wk3Day4 !== null">
              <span  style="margin-left: 10px;">{{ scope.row.wk3Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk3Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk3Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk3" header-align="center" :label="a035" prop="wk3Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk3Day5Status === -1">
                  <span v-if="scope.row.wk3Day5 !== null">
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk3Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk3Day5Status !== -1">
                  <span v-if="scope.row.wk3Day5 !== null">
              <span style="margin-left: 10px;">{{ scope.row.wk3Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk3Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk3Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk3" header-align="center" :label="a036" prop="wk3Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk3Day6Status === -1">
                  <span v-if="scope.row.wk3Day6 !== null">
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk3Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk3Day6Status !== -1">
                  <span v-if="scope.row.wk3Day6 !== null">
              <span style="margin-left: 10px;">{{ scope.row.wk3Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk3Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk3Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk3" header-align="center" :label="a037" prop="wk3Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk3Day7Status === -1">
                  <span v-if="scope.row.wk3Day7 !== null">
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk3Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk3Day7Status !== -1">
                  <span v-if="scope.row.wk3Day7 !== null">
              <span style="margin-left: 10px;">{{ scope.row.wk3Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk3Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk3Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK4-Country" prop="wk4Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk4Status === -1">
                <span v-if="scope.row.wk4Msrp !== null">
              <span @click="showWk4Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk4Msrp }}</span>
               <b v-if="b041">+</b>
                            <b v-if="b042">-</b>
                </span>
              </span>   
              <span v-else-if="scope.row.wk4Status !== -1">
                <span v-if="scope.row.wk4Msrp !== null">
              <span @click="showWk4Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk4Msrp }}</span>
               <b v-if="b041">+</b>
                            <b v-if="b042">-</b>
                </span>
              </span>   
          </template>
      </el-table-column>
              <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK4-USD" prop="wk4MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk4Status === -1">
            <span v-if="scope.row.wk4MsrpUSD !== null">
              <span @click="showWk4Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk4MsrpUSD }}</span>
               <b v-if="b041">+</b>
                            <b v-if="b042">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk4Status !== -1">
                <span v-if="scope.row.wk4MsrpUSD !== null">
                  <span @click="showWk4Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk4MsrpUSD }}</span>
                   <b v-if="b041">+</b>
                            <b v-if="b042">-</b>
               </span>
                <span v-else-if="scope.row.wk4Msrp !== null">
                          <span @click="showWk4Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk4Msrp }}</span>
                           <b v-if="b041">+</b>
                            <b v-if="b042">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk4" header-align="center" :label="a041" prop="wk4Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk4Day1Status === -1">
                  <span v-if="scope.row.wk4Day1 !== null">
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk4Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk4Day1Status !== -1">
                  <span v-if="scope.row.wk4Day1 !== null">
              <span style="margin-left: 10px;">{{ scope.row.wk4Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk4Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk4Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk4" header-align="center" :label="a042" prop="wk4Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk4Day2Status === -1">
                  <span v-if="scope.row.wk4Day2 !== null">
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk4Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk4Day2Status !== -1">
                  <span v-if="scope.row.wk4Day2 !== null">
              <span  style="margin-left: 10px;">{{ scope.row.wk4Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk1Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk4Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk4" header-align="center" :label="a043" prop="wk4Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk4Day3Status === -1">
                  <span v-if="scope.row.wk4Day3 !== null">
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk4Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk4Day3Status !== -1">
                  <span v-if="scope.row.wk4Day3 !== null">
              <span  style="margin-left: 10px;">{{ scope.row.wk4Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk4Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk4Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk4" header-align="center" :label="a044" prop="wk4Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk4Day4Status === -1">
                  <span v-if="scope.row.wk4Day4 !== null">
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk4Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk1Day4Status !== -1">
                  <span v-if="scope.row.wk4Day4 !== null">
              <span  style="margin-left: 10px;">{{ scope.row.wk4Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk4Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk4Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk4" header-align="center" :label="a045" prop="wk4Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk4Day5Status === -1">
                  <span v-if="scope.row.wk4Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk4Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk4Day5Status !== -1">
                  <span v-if="scope.row.wk4Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk4Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk4Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk4Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk4" header-align="center" :label="a046" prop="wk4Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk4Day6Status === -1">
                  <span v-if="scope.row.wk4Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk4Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk4Day6Status !== -1">
                  <span v-if="scope.row.wk4Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk4Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk4Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk4Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk4" header-align="center" :label="a047" prop="wk4Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk4Day7Status === -1">
                  <span v-if="scope.row.wk4Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk4Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk4Day7Status !== -1">
                  <span v-if="scope.row.wk4Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk4Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk4Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk4Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK5-Country" prop="wk5Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk5Status === -1">
                <span v-if="scope.row.wk5Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk5Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk5Msrp }}</span>
               <b v-if="b051">+</b>
                            <b v-if="b052">-</b>
                </span>
              </span>   
              <span v-else-if="scope.row.wk5Status !== -1">
                <span v-if="scope.row.wk5Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk5Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk5Msrp }}</span>
              <b v-if="b051">+</b>
                            <b v-if="b052">-</b>
                </span>
                 <!-- <span v-else-if="scope.row.wk5Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk5Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK5-USD" prop="wk5MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk5Status === -1">
            <span v-if="scope.row.wk5MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk5Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk5MsrpUSD }}</span>
              <b v-if="b051">+</b>
                            <b v-if="b052">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk5Status !== -1">
                <span v-if="scope.row.wk5MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk5Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk5MsrpUSD }}</span>
                  <b v-if="b051">+</b>
                            <b v-if="b052">-</b>
               </span>
                <span v-else-if="scope.row.wk5Msrp !== null">
                          <span @click="showWk5Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk5Msrp }}</span>
                          <b v-if="b051">+</b>
                            <b v-if="b052">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk5" header-align="center" :label="a051" prop="wk5Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk5Day1Status === -1">
                  <span v-if="scope.row.wk5Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk5Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk5Day1Status !== -1">
                  <span v-if="scope.row.wk5Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk5Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk5Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk5Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk5" header-align="center" :label="a052" prop="wk5Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk5Day2Status === -1">
                  <span v-if="scope.row.wk5Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk5Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk5Day2Status !== -1">
                  <span v-if="scope.row.wk5Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk5Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk5Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk5Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk5" header-align="center" :label="a053" prop="wk5Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk5Day3Status === -1">
                  <span v-if="scope.row.wk5Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk5Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk5Day3Status !== -1">
                  <span v-if="scope.row.wk5Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk5Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk5Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk5Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk5" header-align="center" :label="a054" prop="wk5Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk5Day4Status === -1">
                  <span v-if="scope.row.wk5Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk5Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk5Day4Status !== -1">
                  <span v-if="scope.row.wk5Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk5Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk5Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk5Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk5" header-align="center" :label="a055" prop="wk5Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk5Day5Status === -1">
                  <span v-if="scope.row.wk5Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk5Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk5Day5Status !== -1">
                  <span v-if="scope.row.wk5Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk5Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk5Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk5Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk5" header-align="center" :label="a056" prop="wk5Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk5Day6Status === -1">
                  <span v-if="scope.row.wk5Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk5Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk5Day6Status !== -1">
                  <span v-if="scope.row.wk5Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk5Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk5Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk5Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk5" header-align="center" :label="a057" prop="wk5Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk5Day7Status === -1">
                  <span v-if="scope.row.wk5Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk5Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk5Day7Status !== -1">
                  <span v-if="scope.row.wk5Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk5Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk5Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk5Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK6-Country" prop="wk6Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk6Status === -1">
                <span v-if="scope.row.wk6Msrp !== null">
                <!-- <b>$</b> -->
              <span  @click="showWk6Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk6Msrp }}</span>
              <b v-if="b061">+</b>
                            <b v-if="b062">-</b>
                </span>
              </span>   
              <span v-else-if="scope.row.wk6Status !== -1">
                <span v-if="scope.row.wk6Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk6Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk6Msrp }}</span>
                 <b v-if="b061">+</b>
                            <b v-if="b062">-</b>
                </span>
                 <!-- <span v-else-if="scope.row.wk6Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk6Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
       <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK6-USD" prop="wk6MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk6Status === -1">
            <span v-if="scope.row.wk6MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk6Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk6MsrpUSD }}</span>
                 <b v-if="b061">+</b>
                            <b v-if="b062">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk6Status !== -1">
                <span v-if="scope.row.wk6MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk6Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk6MsrpUSD }}</span>
                     <b v-if="b061">+</b>
                            <b v-if="b062">-</b>
               </span>
                <span v-else-if="scope.row.wk6Msrp !== null">
                          <span @click="showWk6Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk6Msrp }}</span>
                             <b v-if="b061">+</b>
                            <b v-if="b062">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk6" header-align="center" :label="a061" prop="wk6Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk6Day1Status === -1">
                  <span v-if="scope.row.wk6Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk6Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk6Day1Status !== -1">
                  <span v-if="scope.row.wk6Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk6Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk6Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk6Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk6" header-align="center" :label="a062" prop="wk6Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk6Day2Status === -1">
                  <span v-if="scope.row.wk6Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk6Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk6Day2Status !== -1">
                  <span v-if="scope.row.wk6Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk6Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk6Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk6Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk6" header-align="center" :label="a063" prop="wk6Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk6Day3Status === -1">
                  <span v-if="scope.row.wk6Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk6Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk6Day3 !== -1">
                  <span v-if="scope.row.wk1Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk6Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk6Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk6Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk6" header-align="center" :label="a064" prop="wk6Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk6Day4Status === -1">
                  <span v-if="scope.row.wk6Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk6Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk6Day4Status !== -1">
                  <span v-if="scope.row.wk6Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk6Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk6Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk6Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk6" header-align="center" :label="a065" prop="wk6Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk6Day5Status === -1">
                  <span v-if="scope.row.wk6Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk6Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk6Day5Status !== -1">
                  <span v-if="scope.row.wk6Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk6Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk6Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk6Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk6" header-align="center" :label="a066" prop="wk6Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk6Day6Status === -1">
                  <span v-if="scope.row.wk6Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk6Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk6Day6Status !== -1">
                  <span v-if="scope.row.wk6Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk6Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk6Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk6Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk6" header-align="center" :label="a067" prop="wk6Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk6Day7Status === -1">
                  <span v-if="scope.row.wk6Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk6Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk6Day7Status !== -1">
                  <span v-if="scope.row.wk6Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk6Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk6Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk6Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK7-Country" prop="wk7Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk7Status === -1">
                <span v-if="scope.row.wk7Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk7Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk7MsrpCountry }}</span>
                 <b v-if="b071">+</b>
                            <b v-if="b072">-</b>
                </span>
              </span>   
              <span v-else-if="scope.row.wk7Status !== -1">
                <span v-if="scope.row.wk7Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk7Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk7MsrpCountry }}</span>
                <b v-if="b071">+</b>
                            <b v-if="b072">-</b>
                </span>
                 <!-- <span v-else-if="scope.row.wk7Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk7Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
         <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK7-USD" prop="wk7MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk7Status === -1">
            <span v-if="scope.row.wk7MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk7Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk7MsrpUSD }}</span>
                <b v-if="b071">+</b>
                            <b v-if="b072">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk7Status !== -1">
                <span v-if="scope.row.wk7MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk7Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk7MsrpUSD }}</span>
                    <b v-if="b071">+</b>
                            <b v-if="b072">-</b>
               </span>
                <span v-else-if="scope.row.wk7Msrp !== null">
                          <span @click="showWk7Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk7Msrp }}</span>
                            <b v-if="b071">+</b>
                            <b v-if="b072">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk7" header-align="center" :label="a071" prop="wk7Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk7Day1Status === -1">
                  <span v-if="scope.row.wk7Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk7Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk7Day1Status !== -1">
                  <span v-if="scope.row.wk7Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk7Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk7Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk7Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk7" header-align="center" :label="a072" prop="wk7Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk7Day2Status === -1">
                  <span v-if="scope.row.wk7Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk7Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk7Day2Status !== -1">
                  <span v-if="scope.row.wk7Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk7Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk7Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk7Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk7" header-align="center" :label="a073" prop="wk7Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk7Day3Status === -1">
                  <span v-if="scope.row.wk7Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk7Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk7Day3Status !== -1">
                  <span v-if="scope.row.wk7Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk7Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk7Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk7Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk7" header-align="center" :label="a074" prop="wk7Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk7Day4Status === -1">
                  <span v-if="scope.row.wk7Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk7Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk7Day4Status !== -1">
                  <span v-if="scope.row.wk7Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk7Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk7Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk7Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk7" header-align="center" :label="a075" prop="wk7Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk7Day5Status === -1">
                  <span v-if="scope.row.wk7Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk7Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk7Day5Status !== -1">
                  <span v-if="scope.row.wk7Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk7Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk7Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk7Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk7" header-align="center" :label="a076" prop="wk7Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk7Day6Status === -1">
                  <span v-if="scope.row.wk7Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk7Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk7Day6Status !== -1">
                  <span v-if="scope.row.wk7Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk7Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk7Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk7Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk7" header-align="center" :label="a077" prop="wk7Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk7Day7Status === -1">
                  <span v-if="scope.row.wk7Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk7Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk7Day7Status !== -1">
                  <span v-if="scope.row.wk7Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk7Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk7Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk7Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK8-Country" prop="wk8Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk8Status === -1">
                <span v-if="scope.row.wk8Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk8Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk8Msrp }}</span>
                <b v-if="b081">+</b>
                            <b v-if="b082">-</b>
                </span>
              </span>   
              <span v-else-if="scope.row.wk8Status !== -1">
                <span v-if="scope.row.wk8Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk8Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk8Msrp }}</span>
                <b v-if="b081">+</b>
                            <b v-if="b082">-</b>
                </span>
                 <!-- <span v-else-if="scope.row.wk8Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk8Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK8-USD" prop="wk8MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk8Status === -1">
            <span v-if="scope.row.wk8MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk8Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk8MsrpUSD }}</span>
                <b v-if="b081">+</b>
                            <b v-if="b082">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk8Status !== -1">
                <span v-if="scope.row.wk8MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk8Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk8MsrpUSD }}</span>
                    <b v-if="b081">+</b>
                            <b v-if="b082">-</b>
               </span>
                <span v-else-if="scope.row.wk8Msrp !== null">
                          <span @click="showWk8Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk8Msrp }}</span>
                            <b v-if="b081">+</b>
                            <b v-if="b082">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk8" header-align="center" :label="a081" prop="wk8Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk8Day1Status === -1">
                  <span v-if="scope.row.wk8Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk8Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk8Day1Status !== -1">
                  <span v-if="scope.row.wk8Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk8Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk8Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk8Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk8" header-align="center" :label="a082" prop="wk8Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk8Day2Status === -1">
                  <span v-if="scope.row.wk8Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk8Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk8Day2Status !== -1">
                  <span v-if="scope.row.wk8Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk8Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk8Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk8Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk8" header-align="center" :label="a083" prop="wk8Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk8Day3Status === -1">
                  <span v-if="scope.row.wk8Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk8Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk8Day3Status !== -1">
                  <span v-if="scope.row.wk8Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk8Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk8Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk8Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk8" header-align="center" :label="a084" prop="wk8Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk8Day4Status === -1">
                  <span v-if="scope.row.wk8Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk8Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk8Day4Status !== -1">
                  <span v-if="scope.row.wk8Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk8Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk8Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk8Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk8" header-align="center" :label="a085" prop="wk8Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk8Day5Status === -1">
                  <span v-if="scope.row.wk8Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk8Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk8Day5Status !== -1">
                  <span v-if="scope.row.wk8Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk8Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk8Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk8Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk8" header-align="center" :label="a086" prop="wk8Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk8Day6Status === -1">
                  <span v-if="scope.row.wk8Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk8Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk8Day6Status !== -1">
                  <span v-if="scope.row.wk8Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk8Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk8Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk8Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk8" header-align="center" :label="a087" prop="wk8Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk8Day7Status === -1">
                  <span v-if="scope.row.wk8Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk8Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk8Day7Status !== -1">
                  <span v-if="scope.row.wk8Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk8Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk8Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk8Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK9-Country" prop="wk9Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk9Status === -1">
                <span v-if="scope.row.wk9Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk9Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk9Msrp }}</span>
                <b v-if="b091">+</b>
                            <b v-if="b092">-</b>
                </span>
              </span>   
              <span v-else-if="scope.row.wk9Status !== -1">
                <span v-if="scope.row.wk9Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk9Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk9Msrp }}</span>
                <b v-if="b091">+</b>
                            <b v-if="b092">-</b>
                </span>
                 <!-- <span v-else-if="scope.row.wk9Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk9Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK9-USD" prop="wk9MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk9Status === -1">
            <span v-if="scope.row.wk9MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk9Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk9MsrpUSD }}</span>
                <b v-if="b091">+</b>
                            <b v-if="b092">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk9Status !== -1">
                <span v-if="scope.row.wk9MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk9Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk9MsrpUSD }}</span>
                    <b v-if="b091">+</b>
                            <b v-if="b092">-</b>
               </span>
                <span v-else-if="scope.row.wk9Msrp !== null">
                          <span @click="showWk9Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk9Msrp }}</span>
                            <b v-if="b091">+</b>
                            <b v-if="b092">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk9" header-align="center" :label="a091" prop="wk9Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk9Day1Status === -1">
                  <span v-if="scope.row.wk9Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk9Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk9Day1Status !== -1">
                  <span v-if="scope.row.wk9Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk9Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk9Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk9Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk9" header-align="center" :label="a092" prop="wk9Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk9Day2Status === -1">
                  <span v-if="scope.row.wk9Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk9Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk9Day2Status !== -1">
                  <span v-if="scope.row.wk9Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk9Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk9Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk9Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk9" header-align="center" :label="a093" prop="wk9Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk9Day3Status === -1">
                  <span v-if="scope.row.wk9Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk9Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk9Day3Status !== -1">
                  <span v-if="scope.row.wk9Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk9Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk9Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk9Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk9" header-align="center" :label="a094" prop="wk9Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk9Day4Status === -1">
                  <span v-if="scope.row.wk9Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk9Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk9Day4Status !== -1">
                  <span v-if="scope.row.wk9Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk9Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk9Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk9Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk9" header-align="center" :label="a095" prop="wk9Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk9Day5Status === -1">
                  <span v-if="scope.row.wk9Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk9Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk9Day5Status !== -1">
                  <span v-if="scope.row.wk9Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk9Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk9Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk9Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk9" header-align="center" :label="a096" prop="wk9Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk9Day6Status === -1">
                  <span v-if="scope.row.wk9Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk9Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk9Day6Status !== -1">
                  <span v-if="scope.row.wk9Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk9Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk9Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk9Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk9" header-align="center" :label="a097" prop="wk9Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk9Day7Status === -1">
                  <span v-if="scope.row.wk9Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk9Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk9Day7Status !== -1">
                  <span v-if="scope.row.wk9Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk9Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk9Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk9Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK10-Country" prop="wk10Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk10Status === -1">
                <span v-if="scope.row.wk10Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk10Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk10Msrp }}</span>
                <b v-if="b101">+</b>
                            <b v-if="b102">-</b>
                </span>
              </span>   
              <span v-else-if="scope.row.wk10Status !== -1">
                <span v-if="scope.row.wk10Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk10Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk10Msrp }}</span>
                <b v-if="b101">+</b>
                            <b v-if="b102">-</b>
                </span>
                 <!-- <span v-else-if="scope.row.wk10Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk10Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK10-USD" prop="wk10MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk10Status === -1">
            <span v-if="scope.row.wk10MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk10Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk10MsrpUSD }}</span>
                <b v-if="b101">+</b>
                            <b v-if="b102">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk10Status !== -1">
                <span v-if="scope.row.wk10MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk10Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk10MsrpUSD }}</span>
                    <b v-if="b101">+</b>
                            <b v-if="b102">-</b>
               </span>
                <span v-else-if="scope.row.wk10Msrp !== null">
                          <span @click="showWk10Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk10Msrp }}</span>
                            <b v-if="b101">+</b>
                            <b v-if="b102">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk10" header-align="center" :label="a101" prop="wk10Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk10Day1Status === -1">
                  <span v-if="scope.row.wk10Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk10Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk10Day1Status !== -1">
                  <span v-if="scope.row.wk10Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk10Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk10Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk10Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk10" header-align="center" :label="a102" prop="wk10Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk10Day2Status === -1">
                  <span v-if="scope.row.wk10Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk10Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk10Day2Status !== -1">
                  <span v-if="scope.row.wk10Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk10Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk10Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk10Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk10" header-align="center" :label="a103" prop="wk10Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk10Day3Status === -1">
                  <span v-if="scope.row.wk10Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk10Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk10Day3Status !== -1">
                  <span v-if="scope.row.wk10Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk10Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk10Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk10Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk10" header-align="center" :label="a104" prop="wk10Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk10Day4Status === -1">
                  <span v-if="scope.row.wk10Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk10Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk10Day4Status !== -1">
                  <span v-if="scope.row.wk10Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk10Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk10Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk10Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk10" header-align="center" :label="a105" prop="wk10Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk10Day5Status === -1">
                  <span v-if="scope.row.wk10Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk10Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk10Day5Status !== -1">
                  <span v-if="scope.row.wk10Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk10Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk10Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk10Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk10" header-align="center" :label="a106" prop="wk10Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk10Day6Status === -1">
                  <span v-if="scope.row.wk10Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk10Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk10Day6Status !== -1">
                  <span v-if="scope.row.wk10Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk10Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk10Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk10Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk10" header-align="center" :label="a107" prop="wk10Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk10Day7Status === -1">
                  <span v-if="scope.row.wk10Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk10Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk10Day7Status !== -1">
                  <span v-if="scope.row.wk10Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk10Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk10Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk10Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK11-Country" prop="wk11Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk11Status === -1">
                <span v-if="scope.row.wk11Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk11Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk11Msrp }}</span>
                <b v-if="b111">+</b>
                            <b v-if="b112">-</b>
                </span>
              </span>   
              <span v-else-if="scope.row.wk11Status !== -1">
                <span v-if="scope.row.wk11Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk11Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk11Msrp }}</span>
              <b v-if="b111">+</b>
                            <b v-if="b112">-</b>
                </span>
                 <!-- <span v-else-if="scope.row.wk11Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk11Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK11-USD" prop="wk11MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk11Status === -1">
            <span v-if="scope.row.wk11MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk11Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk11MsrpUSD }}</span>
              <b v-if="b111">+</b>
                            <b v-if="b112">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk11Status !== -1">
                <span v-if="scope.row.wk11MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk11Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk11MsrpUSD }}</span>
                  <b v-if="b111">+</b>
                            <b v-if="b112">-</b>
               </span>
                <span v-else-if="scope.row.wk11Msrp !== null">
                          <span @click="showWk11Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk11Msrp }}</span>
                          <b v-if="b111">+</b>
                            <b v-if="b112">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk11" header-align="center" :label="a111" prop="wk11Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk11Day1Status === -1">
                  <span v-if="scope.row.wk11Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk11Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk11Day1Status !== -1">
                  <span v-if="scope.row.wk11Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk11Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk11Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk11Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk11" header-align="center" :label="a112" prop="wk11Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk11Day2Status === -1">
                  <span v-if="scope.row.wk11Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk11Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk11Day2Status !== -1">
                  <span v-if="scope.row.wk11Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk11Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk11Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk11Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk11" header-align="center" :label="a113" prop="wk11Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk11Day3Status === -1">
                  <span v-if="scope.row.wk11Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk11Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk11Day3Status !== -1">
                  <span v-if="scope.row.wk11Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk11Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk11Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk11Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk11" header-align="center" :label="a114" prop="wk11Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk11Day4Status === -1">
                  <span v-if="scope.row.wk11Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk11Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk11Day4Status !== -1">
                  <span v-if="scope.row.wk11Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk11Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk11Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk11Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk11" header-align="center" :label="a115" prop="wk11Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk11Day5Status === -1">
                  <span v-if="scope.row.wk11Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk11Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk11Day5Status !== -1">
                  <span v-if="scope.row.wk11Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk11Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk11Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk11Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk11" header-align="center" :label="a116" prop="wk11Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk11Day6Status === -1">
                  <span v-if="scope.row.wk11Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk11Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk11Day6Status !== -1">
                  <span v-if="scope.row.wk11Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk11Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk11Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk11Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk11" header-align="center" :label="a117" prop="wk11Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk11Day7Status === -1">
                  <span v-if="scope.row.wk11Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk11Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk11Day7Status !== -1">
                  <span v-if="scope.row.wk11Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk11Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk11Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk11Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK12-Country" prop="wk12Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk12Status === -1">
                <span v-if="scope.row.wk12Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk12Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk12Msrp }}</span>
              <b v-if="b121">+</b>
                            <b v-if="b122">-</b>
                </span>
              </span>   
              <span v-else-if="scope.row.wk12Status !== -1">
                <span v-if="scope.row.wk12Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk12Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk12Msrp }}</span>
               <b v-if="b121">+</b>
                            <b v-if="b122">-</b>
                </span>
                 <!-- <span v-else-if="scope.row.wk12Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk12Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK12-USD" prop="wk12MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk12Status === -1">
            <span v-if="scope.row.wk12MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk12Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk12MsrpUSD }}</span>
               <b v-if="b121">+</b>
                            <b v-if="b122">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk12Status !== -1">
                <span v-if="scope.row.wk12MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk12Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk12MsrpUSD }}</span>
                   <b v-if="b121">+</b>
                            <b v-if="b122">-</b>
               </span>
                <span v-else-if="scope.row.wk12Msrp !== null">
                          <span @click="showWk12Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk12Msrp }}</span>
                           <b v-if="b121">+</b>
                            <b v-if="b122">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk12" header-align="center" :label="a121" prop="wk12Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk12Day1Status === -1">
                  <span v-if="scope.row.wk12Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk12Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk12Day1Status !== -1">
                  <span v-if="scope.row.wk12Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk12Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk12Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk12Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk12" header-align="center" :label="a122" prop="wk12Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk12Day2Status === -1">
                  <span v-if="scope.row.wk12Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk12Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk12Day2Status !== -1">
                  <span v-if="scope.row.wk12Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk12Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk12Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk12Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk12" header-align="center" :label="a123" prop="wk12Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk12Day3Status === -1">
                  <span v-if="scope.row.wk12Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk12Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk12Day3Status !== -1">
                  <span v-if="scope.row.wk12Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk12Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk12Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk12Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk12" header-align="center" :label="a124" prop="wk12Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk12Day4Status === -1">
                  <span v-if="scope.row.wk12Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk12Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk12Day4Status !== -1">
                  <span v-if="scope.row.wk12Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk12Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk12Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk12Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk12" header-align="center" :label="a125" prop="wk12Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk12Day5Status === -1">
                  <span v-if="scope.row.wk12Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk12Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk12Day5Status !== -1">
                  <span v-if="scope.row.wk12Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk12Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk12Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk12Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk12" header-align="center" :label="a126" prop="wk12Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk12Day6Status === -1">
                  <span v-if="scope.row.wk12Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk12Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk12Day6Status !== -1">
                  <span v-if="scope.row.wk12Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk12Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk12Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk12Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk12" header-align="center" :label="a127" prop="wk12Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk12Day7Status === -1">
                  <span v-if="scope.row.wk12Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk12Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk12Day7Status !== -1">
                  <span v-if="scope.row.wk12Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk12Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk12Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk12Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK13-Country" prop="wk13Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk13Status === -1">
                <span v-if="scope.row.wk13Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk13Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk13Msrp }}</span>
               <b v-if="b131">+</b>
                            <b v-if="b132">-</b>
                </span>
              </span>   
              <span v-else-if="scope.row.wk13Status !== -1">
                <span v-if="scope.row.wk13Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk13Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk13Msrp }}</span>
              <b v-if="b131">+</b>
                            <b v-if="b132">-</b>
                </span>
                 <!-- <span v-else-if="scope.row.wk13Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk13Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK13-USD" prop="wk13MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk13Status === -1">
            <span v-if="scope.row.wk13MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk13Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk13MsrpUSD }}</span>
              <b v-if="b131">+</b>
                            <b v-if="b132">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk13Status !== -1">
                <span v-if="scope.row.wk13MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk13Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk13MsrpUSD }}</span>
                  <b v-if="b131">+</b>
                            <b v-if="b132">-</b>
               </span>
                <span v-else-if="scope.row.wk13Msrp !== null">
                          <span @click="showWk13Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk13Msrp }}</span>
                          <b v-if="b131">+</b>
                            <b v-if="b132">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk13" header-align="center" :label="a131" prop="wk13Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk13Day1Status === -1">
                  <span v-if="scope.row.wk13Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk13Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk13Day1Status !== -1">
                  <span v-if="scope.row.wk13Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk13Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk13Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk13Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk13" header-align="center" :label="a132" prop="wk13Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk13Day2Status === -1">
                  <span v-if="scope.row.wk13Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk13Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk13Day2Status !== -1">
                  <span v-if="scope.row.wk13Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk13Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk13Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk13Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk13" header-align="center" :label="a133" prop="wk13Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk13Day3Status === -1">
                  <span v-if="scope.row.wk13Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk13Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk13Day3Status !== -1">
                  <span v-if="scope.row.wk13Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk13Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk13Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk13Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk13" header-align="center" :label="a134" prop="wk13Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk13Day4Status === -1">
                  <span v-if="scope.row.wk13Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk13Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk13Day4Status !== -1">
                  <span v-if="scope.row.wk13Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk13Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk13Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk13Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk13" header-align="center" :label="a135" prop="wk13Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk13Day5Status === -1">
                  <span v-if="scope.row.wk13Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk13Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk13Day5Status !== -1">
                  <span v-if="scope.row.wk13Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk13Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk13Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk13Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk13" header-align="center" :label="a136" prop="wk13Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk13Day6Status === -1">
                  <span v-if="scope.row.wk13Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk13Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk13Day6Status !== -1">
                  <span v-if="scope.row.wk13Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk13Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk13Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk13Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk13" header-align="center" :label="a137" prop="wk13Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk13Day7Status === -1">
                  <span v-if="scope.row.wk13Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk13Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk13Day7Status !== -1">
                  <span v-if="scope.row.wk13Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk13Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk13Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk13Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK14-Country" prop="wk14Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk14Status === -1">
                <span v-if="scope.row.wk14Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk14Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk14Msrp }}</span>
              <b v-if="b141">+</b>
                            <b v-if="b142">-</b>
                </span>
              </span>   
              <span v-else-if="scope.row.wk14Status !== -1">
                <span v-if="scope.row.wk14Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk14Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk14Msrp }}</span>
               <b v-if="b141">+</b>
                            <b v-if="b142">-</b>
                </span>
                 <!-- <span v-else-if="scope.row.wk14Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk14Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK14-USD" prop="wk14MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk14Status === -1">
            <span v-if="scope.row.wk14MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk14Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk14MsrpUSD }}</span>
               <b v-if="b141">+</b>
                            <b v-if="b142">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk14Status !== -1">
                <span v-if="scope.row.wk14MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk14Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk14MsrpUSD }}</span>
                   <b v-if="b141">+</b>
                            <b v-if="b142">-</b>
               </span>
                <span v-else-if="scope.row.wk14Msrp !== null">
                          <span @click="showWk14Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk14Msrp }}</span>
                           <b v-if="b141">+</b>
                            <b v-if="b142">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk14" header-align="center" :label="a141" prop="wk14Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk14Day1Status === -1">
                  <span v-if="scope.row.wk14Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk14Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk14Day1Status !== -1">
                  <span v-if="scope.row.wk14Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk14Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk14Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk14Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk14" header-align="center" :label="a142" prop="wk14Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk14Day2Status === -1">
                  <span v-if="scope.row.wk14Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk14Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk14Day2Status !== -1">
                  <span v-if="scope.row.wk14Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk14Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk14Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk14Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk14" header-align="center" :label="a143" prop="wk14Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk14Day3Status === -1">
                  <span v-if="scope.row.wk14Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk14Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk14Day3Status !== -1">
                  <span v-if="scope.row.wk14Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk14Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk14Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk14Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk14" header-align="center" :label="a144" prop="wk14Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk14Day4Status === -1">
                  <span v-if="scope.row.wk14Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk14Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk14Day4Status !== -1">
                  <span v-if="scope.row.wk14Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk14Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk14Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk14Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk14" header-align="center" :label="a145" prop="wk14Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk14Day5Status === -1">
                  <span v-if="scope.row.wk14Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk14Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk14Day5Status !== -1">
                  <span v-if="scope.row.wk14Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk14Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk14Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk14Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk14" header-align="center" :label="a146" prop="wk14Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk14Day6Status === -1">
                  <span v-if="scope.row.wk14Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk14Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk14Day6Status !== -1">
                  <span v-if="scope.row.wk14Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk14Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk14Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk14Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk14" header-align="center" :label="a147" prop="wk14Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk14Day7Status === -1">
                  <span v-if="scope.row.wk14Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk14Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk14Day7Status !== -1">
                  <span v-if="scope.row.wk14Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk14Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk14Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk14Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK15-Country" prop="wk15Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk15Status === -1">
                <span v-if="scope.row.wk15Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk15Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk15Msrp }}</span>
               <b v-if="b151">+</b>
                            <b v-if="b152">-</b>
                </span>
              </span>   
              <span v-else-if="scope.row.wk15Status !== -1">
                <span v-if="scope.row.wk15Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk15Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk15Msrp }}</span>
               <b v-if="b151">+</b>
                            <b v-if="b152">-</b>
                </span>
                 <!-- <span v-else-if="scope.row.wk15Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk15Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK15-USD" prop="wk15MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk15Status === -1">
            <span v-if="scope.row.wk15MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk15Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk15MsrpUSD }}</span>
               <b v-if="b151">+</b>
                            <b v-if="b152">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk15Status !== -1">
                <span v-if="scope.row.wk15MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk15Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk15MsrpUSD }}</span>
                   <b v-if="b151">+</b>
                            <b v-if="b152">-</b>
               </span>
                <span v-else-if="scope.row.wk15Msrp !== null">
                          <span @click="showWk15Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk15Msrp }}</span>
                           <b v-if="b151">+</b>
                            <b v-if="b152">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk15" header-align="center" :label="a151" prop="wk15Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk15Day1Status === -1">
                  <span v-if="scope.row.wk15Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk15Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk15Day1Status !== -1">
                  <span v-if="scope.row.wk15Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk15Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk15Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk15Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk15" header-align="center" :label="a152" prop="wk15Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk15Day2Status === -1">
                  <span v-if="scope.row.wk15Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk15Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk15Day2Status !== -1">
                  <span v-if="scope.row.wk15Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk15Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk15Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk15Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk15" header-align="center" :label="a153" prop="wk15Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk15Day3Status === -1">
                  <span v-if="scope.row.wk15Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk15Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk15Day3Status !== -1">
                  <span v-if="scope.row.wk15Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk15Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk15Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk15Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk15" header-align="center" :label="a154" prop="wk15Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk15Day4Status === -1">
                  <span v-if="scope.row.wk15Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk15Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk15Day4Status !== -1">
                  <span v-if="scope.row.wk15Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk15Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk15Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk15Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk15" header-align="center" :label="a155" prop="wk15Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk15Day5Status === -1">
                  <span v-if="scope.row.wk15Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk15Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk15Day5Status !== -1">
                  <span v-if="scope.row.wk15Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk15Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk15Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk15Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk15" header-align="center" :label="a156" prop="wk15Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk15Day6Status === -1">
                  <span v-if="scope.row.wk15Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk15Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk15Day6Status !== -1">
                  <span v-if="scope.row.wk15Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk15Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk15Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk15Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk15" header-align="center" :label="a157" prop="wk15Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk15Day7Status === -1">
                  <span v-if="scope.row.wk15Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk15Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk15Day7Status !== -1">
                  <span v-if="scope.row.wk15Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk15Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk15Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk15Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK16-Country" prop="wk16Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk16Status === -1">
                <span v-if="scope.row.wk16Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk16Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk16Msrp }}</span>
               <b v-if="b161">+</b>
                            <b v-if="b162">-</b>
                </span>
              </span>   
              <span v-else-if="scope.row.wk16Status !== -1">
                 <span v-if="scope.row.wk16Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk16Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk16Msrp }}</span>
               <b v-if="b161">+</b>
                            <b v-if="b162">-</b>
                 </span>
                  <!-- <span v-else-if="scope.row.wk16Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk16Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK16-USD" prop="wk16MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk16Status === -1">
            <span v-if="scope.row.wk16MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk16Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk16MsrpUSD }}</span>
               <b v-if="b161">+</b>
                            <b v-if="b162">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk16Status !== -1">
                <span v-if="scope.row.wk16MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk16Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk16MsrpUSD }}</span>
                   <b v-if="b161">+</b>
                            <b v-if="b162">-</b>
               </span>
                <span v-else-if="scope.row.wk16Msrp !== null">
                          <span @click="showWk16Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk16Msrp }}</span>
                           <b v-if="b161">+</b>
                            <b v-if="b162">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk16" header-align="center" :label="a161" prop="wk16Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk16Day1Status === -1">
                  <span v-if="scope.row.wk16Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk16Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk16Day1Status !== -1">
                  <span v-if="scope.row.wk16Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk16Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk16Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk16Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk16" header-align="center" :label="a162" prop="wk16Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk16Day2Status === -1">
                  <span v-if="scope.row.wk16Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk16Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk16Day2Status !== -1">
                  <span v-if="scope.row.wk16Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk16Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk16Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk16Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk16" header-align="center" :label="a163" prop="wk16Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk16Day3Status === -1">
                  <span v-if="scope.row.wk16Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk16Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk16Day3Status !== -1">
                  <span v-if="scope.row.wk16Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk16Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk16Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk16Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk16" header-align="center" :label="a164" prop="wk16Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk16Day4Status === -1">
                  <span v-if="scope.row.wk16Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk16Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk16Day4Status !== -1">
                  <span v-if="scope.row.wk16Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk16Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk16Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk16Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk16" header-align="center" :label="a165" prop="wk16Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk16Day5Status === -1">
                  <span v-if="scope.row.wk16Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk16Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk16Day5Status !== -1">
                  <span v-if="scope.row.wk16Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk16Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk16Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk16Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk16" header-align="center" :label="a166" prop="wk16Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk16Day6Status === -1">
                  <span v-if="scope.row.wk16Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk16Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk16Day6Status !== -1">
                  <span v-if="scope.row.wk16Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk16Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk16Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk16Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk16" header-align="center" :label="a167" prop="wk16Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk16Day7Status === -1">
                  <span v-if="scope.row.wk16Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk16Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk16Day7Status !== -1">
                  <span v-if="scope.row.wk16Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk16Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk16Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk16Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK17-Country" prop="wk17Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk17Status === -1">
                 <span v-if="scope.row.wk17Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk17Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk17Msrp }}</span>
               <b v-if="b171">+</b>
                            <b v-if="b172">-</b>
                 </span>
              </span>   
              <span v-else-if="scope.row.wk17Status !== -1">
                 <span v-if="scope.row.wk17Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk17Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk17Msrp }}</span>
                <b v-if="b171">+</b>
                            <b v-if="b172">-</b>
                 </span>
                  <!-- <span v-else-if="scope.row.wk17Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk17Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK17-USD" prop="wk17MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk17Status === -1">
            <span v-if="scope.row.wk17MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk17Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk17MsrpUSD }}</span>
                <b v-if="b171">+</b>
                            <b v-if="b172">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk17Status !== -1">
                <span v-if="scope.row.wk17MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk17Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk17MsrpUSD }}</span>
                    <b v-if="b171">+</b>
                            <b v-if="b172">-</b>
               </span>
                <span v-else-if="scope.row.wk17Msrp !== null">
                          <span @click="showWk17Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk17Msrp }}</span>
                            <b v-if="b171">+</b>
                            <b v-if="b172">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk17" header-align="center" :label="a171" prop="wk17Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk17Day1Status === -1">
                  <span v-if="scope.row.wk17Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk17Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk17Day1Status !== -1">
                  <span v-if="scope.row.wk17Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk17Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk17Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk17Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk17" header-align="center" :label="a172" prop="wk17Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk17Day2Status === -1">
                  <span v-if="scope.row.wk17Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk17Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk17Day2Status !== -1">
                  <span v-if="scope.row.wk17Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk17Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk17Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk17Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk17" header-align="center" :label="a173" prop="wk17Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk17Day3Status === -1">
                  <span v-if="scope.row.wk17Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk17Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk17Day3Status !== -1">
                  <span v-if="scope.row.wk17Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk17Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk17Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk17Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk17" header-align="center" :label="a174" prop="wk17Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk17Day4Status === -1">
                  <span v-if="scope.row.wk17Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk17Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk17Day4Status !== -1">
                  <span v-if="scope.row.wk17Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk17Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk17Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk17Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk17" header-align="center" :label="a175" prop="wk17Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk17Day5Status === -1">
                  <span v-if="scope.row.wk17Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk17Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk17Day5Status !== -1">
                  <span v-if="scope.row.wk17Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk17Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk17Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk17Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk17" header-align="center" :label="a176" prop="wk17Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk17Day6Status === -1">
                  <span v-if="scope.row.wk17Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk17Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk17Day6Status !== -1">
                  <span v-if="scope.row.wk17Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk17Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk17Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk17Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk17" header-align="center" :label="a177" prop="wk17Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk17Day7Status === -1">
                  <span v-if="scope.row.wk17Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk17Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk17Day7Status !== -1">
                  <span v-if="scope.row.wk17Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk17Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk17Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk17Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK18-Country" prop="wk18Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk18Status === -1">
                 <span v-if="scope.row.wk18Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk18Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk18Msrp }}</span>
                <b v-if="b181">+</b>
                            <b v-if="b182">-</b>
                 </span>
              </span>   
              <span v-else-if="scope.row.wk18Status !== -1">
                     <span v-if="scope.row.wk18Msrp !== null">
                        <!-- <b>$</b> -->
                        <span @click="showWk18Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk18Msrp }}</span>
                         <b v-if="b181">+</b>
                            <b v-if="b182">-</b>
                     </span>
                      <!-- <span v-else-if="scope.row.wk18Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk18Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK18-USD" prop="wk18MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk18Status === -1">
            <span v-if="scope.row.wk18MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk18Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk18MsrpUSD }}</span>
               <b v-if="b181">+</b>
                            <b v-if="b182">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk18Status !== -1">
                <span v-if="scope.row.wk18MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk18Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk18MsrpUSD }}</span>
                   <b v-if="b181">+</b>
                            <b v-if="b182">-</b>
               </span>
                <span v-else-if="scope.row.wk18Msrp !== null">
                          <span @click="showWk18Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk18Msrp }}</span>
                           <b v-if="b181">+</b>
                            <b v-if="b182">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk18" header-align="center" :label="a181" prop="wk18Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk18Day1Status === -1">
                  <span v-if="scope.row.wk18Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk18Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk18Day1Status !== -1">
                  <span v-if="scope.row.wk18Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk18Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk18Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk18Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk18" header-align="center" :label="a182" prop="wk18Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk18Day2Status === -1">
                  <span v-if="scope.row.wk18Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk18Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk18Day2Status !== -1">
                  <span v-if="scope.row.wk18Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk18Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk18Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk18Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk18" header-align="center" :label="a183" prop="wk18Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk18Day3Status === -1">
                  <span v-if="scope.row.wk18Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk18Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk18Day3Status !== -1">
                  <span v-if="scope.row.wk18Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk18Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk18Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk18Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk18" header-align="center" :label="a184" prop="wk18Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk18Day4Status === -1">
                  <span v-if="scope.row.wk18Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk18Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk18Day4Status !== -1">
                  <span v-if="scope.row.wk18Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk18Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk18Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk18Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk18" header-align="center" :label="a185" prop="wk18Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk18Day5Status === -1">
                  <span v-if="scope.row.wk18Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk18Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk18Day5Status !== -1">
                  <span v-if="scope.row.wk18Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk18Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk18Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk18Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk18" header-align="center" :label="a186" prop="wk18Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk18Day6Status === -1">
                  <span v-if="scope.row.wk18Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk18Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk18Day6Status !== -1">
                  <span v-if="scope.row.wk18Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk18Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk18Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk18Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk18" header-align="center" :label="a187" prop="wk18Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk18Day7Status === -1">
                  <span v-if="scope.row.wk18Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk18Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk18Day7Status !== -1">
                  <span v-if="scope.row.wk18Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk18Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk18Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk18Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK19-Country" prop="wk19Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk19Status === -1">
                     <span v-if="scope.row.wk19Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk19Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk19Msrp }}</span>
               <b v-if="b191">+</b>
                            <b v-if="b192">-</b>
                     </span>
              </span>   
              <span v-else-if="scope.row.wk19Status !== -1">
                 <span v-if="scope.row.wk19Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk19Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk19Msrp }}</span>
               <b v-if="b191">+</b>
                            <b v-if="b192">-</b>
                 </span>
                  <!-- <span v-else-if="scope.row.wk19Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk19Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK19-USD" prop="wk19MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk19Status === -1">
            <span v-if="scope.row.wk19MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk19Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk19MsrpUSD }}</span>
               <b v-if="b191">+</b>
                            <b v-if="b192">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk19Status !== -1">
                <span v-if="scope.row.wk19MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk19Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk19MsrpUSD }}</span>
                   <b v-if="b191">+</b>
                            <b v-if="b192">-</b>
               </span>
                <span v-else-if="scope.row.wk19Msrp !== null">
                          <span @click="showWk19Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk19Msrp }}</span>
                           <b v-if="b191">+</b>
                            <b v-if="b192">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk19" header-align="center" :label="a191" prop="wk19Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk19Day1Status === -1">
                  <span v-if="scope.row.wk19Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk19Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk19Day1Status !== -1">
                  <span v-if="scope.row.wk19Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk19Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk19Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk19Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk19" header-align="center" :label="a192" prop="wk19Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk19Day2Status === -1">
                  <span v-if="scope.row.wk19Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk19Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk1Day2Status !== -1">
                  <span v-if="scope.row.wk19Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk19Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk19Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk19Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk19" header-align="center" :label="a193" prop="wk19Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk19Day3Status === -1">
                  <span v-if="scope.row.wk19Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk19Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk19Day3Status !== -1">
                  <span v-if="scope.row.wk19Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk19Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk19Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk19Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk19" header-align="center" :label="a194" prop="wk19Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk19Day4Status === -1">
                  <span v-if="scope.row.wk19Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk19Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk19Day4Status !== -1">
                  <span v-if="scope.row.wk19Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk19Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk19Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk19Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk19" header-align="center" :label="a195" prop="wk19Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk19Day5Status === -1">
                  <span v-if="scope.row.wk19Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk19Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk19Day5Status !== -1">
                  <span v-if="scope.row.wk19Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk19Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk19Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk19Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk19" header-align="center" :label="a196" prop="wk19Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk19Day6Status === -1">
                  <span v-if="scope.row.wk19Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk19Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk19Day6Status !== -1">
                  <span v-if="scope.row.wk19Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk19Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk19Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk19Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk19" header-align="center" :label="a197" prop="wk19Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk19Day7Status === -1">
                  <span v-if="scope.row.wk19Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk19Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk19Day7Status !== -1">
                  <span v-if="scope.row.wk19Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk19Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk19Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk19Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK20-Country" prop="wk20Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk20Status === -1">
                 <span v-if="scope.row.wk20Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk20Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk20Msrp }}</span>
               <b v-if="b201">+</b>
                            <b v-if="b202">-</b>
                 </span>
              </span>   
              <span v-else-if="scope.row.wk20Status !== -1">
                  <span v-if="scope.row.wk20Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk20Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk20Msrp }}</span>
                  <b v-if="b201">+</b>
                            <b v-if="b202">-</b>
                  </span>
                   <!-- <span v-else-if="scope.row.wk20Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk20Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK20-USD" prop="wk20MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk20Status === -1">
            <span v-if="scope.row.wk20MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk20Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk20MsrpUSD }}</span>
                  <b v-if="b201">+</b>
                            <b v-if="b202">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk20Status !== -1">
                <span v-if="scope.row.wk20MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk20Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk20MsrpUSD }}</span>
                      <b v-if="b201">+</b>
                            <b v-if="b202">-</b>
               </span>
                <span v-else-if="scope.row.wk20Msrp !== null">
                          <span @click="showWk20Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk20Msrp }}</span>
                              <b v-if="b201">+</b>
                            <b v-if="b202">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk20" header-align="center" :label="a201" prop="wk20Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk20Day1Status === -1">
                  <span v-if="scope.row.wk20Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk20Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk20Day1Status !== -1">
                  <span v-if="scope.row.wk20Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk20Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk20Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk20Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk20" header-align="center" :label="a202" prop="wk20Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk20Day2Status === -1">
                  <span v-if="scope.row.wk20Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk20Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk20Day2Status !== -1">
                  <span v-if="scope.row.wk20Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk20Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk20Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk20Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk20" header-align="center" :label="a203" prop="wk20Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk20Day3Status === -1">
                  <span v-if="scope.row.wk20Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk20Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk20Day3Status !== -1">
                  <span v-if="scope.row.wk20Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk20Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk20Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk20Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk20" header-align="center" :label="a204" prop="wk20Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk20Day4Status === -1">
                  <span v-if="scope.row.wk20Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk20Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk20Day4Status !== -1">
                  <span v-if="scope.row.wk20Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk20Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk20Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk20Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk20" header-align="center" :label="a205" prop="wk20Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk20Day5Status === -1">
                  <span v-if="scope.row.wk20Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk20Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk20Day5Status !== -1">
                  <span v-if="scope.row.wk20Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk20Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk20Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk20Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk20" header-align="center" :label="a206" prop="wk20Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk20Day6Status === -1">
                  <span v-if="scope.row.wk20Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk20Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk20Day6Status !== -1">
                  <span v-if="scope.row.wk20Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk20Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk20Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk20Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk20" header-align="center" :label="a207" prop="wk20Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk20Day7Status === -1">
                  <span v-if="scope.row.wk20Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk20Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk20Day7Status !== -1">
                  <span v-if="scope.row.wk20Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk20Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk20Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk20Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK21-Country" prop="wk21Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk21Status === -1">
                  <span v-if="scope.row.wk21Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk21Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk21Msrp }}</span>
                  <b v-if="b211">+</b>
                            <b v-if="b212">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk21Status !== -1">
                  <span v-if="scope.row.wk21Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk21Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk21Msrp }}</span>
                <b v-if="b211">+</b>
                            <b v-if="b212">-</b>
                  </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK21-USD" prop="wk21MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk21Status === -1">
            <span v-if="scope.row.wk21MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk21Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk21MsrpUSD }}</span>
                <b v-if="b211">+</b>
                            <b v-if="b212">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk21Status !== -1">
                <span v-if="scope.row.wk21MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk21Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk21MsrpUSD }}</span>
                    <b v-if="b211">+</b>
                            <b v-if="b212">-</b>
               </span>
                <span v-else-if="scope.row.wk21Msrp !== null">
                          <span @click="showWk21Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk21Msrp }}</span>
                            <b v-if="b211">+</b>
                            <b v-if="b212">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk21" header-align="center" :label="a211" prop="wk21Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk21Day1Status === -1">
                  <span v-if="scope.row.wk21Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk21Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk21Day1Status !== -1">
                  <span v-if="scope.row.wk21Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk21Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk21Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk21Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk21" header-align="center" :label="a212" prop="wk21Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk21Day2Status === -1">
                  <span v-if="scope.row.wk21Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk21Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk21Day2Status !== -1">
                  <span v-if="scope.row.wk21Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk21Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk21Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk21Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk21" header-align="center" :label="a213" prop="wk21Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk21Day3Status === -1">
                  <span v-if="scope.row.wk21Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk21Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk21Day3Status !== -1">
                  <span v-if="scope.row.wk21Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk21Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk21Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk21Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk21" header-align="center" :label="a214" prop="wk21Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk21Day4Status === -1">
                  <span v-if="scope.row.wk21Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk21Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk21Day4Status !== -1">
                  <span v-if="scope.row.wk21Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk21Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk21Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk21Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk21" header-align="center" :label="a215" prop="wk21Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk21Day5Status === -1">
                  <span v-if="scope.row.wk21Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk21Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk21Day5Status !== -1">
                  <span v-if="scope.row.wk21Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk21Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk21Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk21Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk21" header-align="center" :label="a216" prop="wk21Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk21Day6Status === -1">
                  <span v-if="scope.row.wk21Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk21Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk21Day6Status !== -1">
                  <span v-if="scope.row.wk21Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk21Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk21Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk21Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk21" header-align="center" :label="a217" prop="wk21Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk21Day7Status === -1">
                  <span v-if="scope.row.wk21Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk21Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk21Day7Status !== -1">
                  <span v-if="scope.row.wk21Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk21Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk21Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk21Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK22-Country" prop="wk22Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk22Status === -1">
                  <span v-if="scope.row.wk22Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk22Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk22Msrp }}</span>
                <b v-if="b221">+</b>
                            <b v-if="b222">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk22Status !== -1">
                  <span v-if="scope.row.wk22Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk22Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk22Msrp }}</span>
                 <b v-if="b221">+</b>
                            <b v-if="b222">-</b>
                  </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK22-USD" prop="wk22MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk22Status === -1">
            <span v-if="scope.row.wk22MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk22Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk22MsrpUSD }}</span>
                 <b v-if="b221">+</b>
                            <b v-if="b222">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk22Status !== -1">
                <span v-if="scope.row.wk22MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk22Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk22MsrpUSD }}</span>
                     <b v-if="b221">+</b>
                            <b v-if="b222">-</b>
               </span>
                <span v-else-if="scope.row.wk22Msrp !== null">
                          <span @click="showWk22Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk22Msrp }}</span>
                             <b v-if="b221">+</b>
                            <b v-if="b222">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk22" header-align="center" :label="a221" prop="wk22Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk22Day1Status === -1">
                  <span v-if="scope.row.wk22Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk22Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk22Day1Status !== -1">
                  <span v-if="scope.row.wk22Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk22Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk22Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk22Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk22" header-align="center" :label="a222" prop="wk22Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk22Day2Status === -1">
                  <span v-if="scope.row.wk22Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk22Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk22Day2Status !== -1">
                  <span v-if="scope.row.wk22Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk22Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk22Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk22Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk22" header-align="center" :label="a223" prop="wk22Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk22Day3Status === -1">
                  <span v-if="scope.row.wk22Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk22Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk22Day3Status !== -1">
                  <span v-if="scope.row.wk22Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk22Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk22Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk22Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk22" header-align="center" :label="a224" prop="wk22Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk22Day4Status === -1">
                  <span v-if="scope.row.wk22Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk22Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk22Day4Status !== -1">
                  <span v-if="scope.row.wk22Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk22Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk22Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk22Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk22" header-align="center" :label="a225" prop="wk22Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk22Day5Status === -1">
                  <span v-if="scope.row.wk22Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk22Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk22Day5Status !== -1">
                  <span v-if="scope.row.wk22Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk22Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk22Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk22Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk22" header-align="center" :label="a226" prop="wk22Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk22Day6Status === -1">
                  <span v-if="scope.row.wk22Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk22Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk22Day6Status !== -1">
                  <span v-if="scope.row.wk22Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk22Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk22Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk22Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk22" header-align="center" :label="a227" prop="wk22Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk22Day7Status === -1">
                  <span v-if="scope.row.wk22Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk22Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk22Day7Status !== -1">
                  <span v-if="scope.row.wk22Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk22Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk22Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk22Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK23-Country" prop="wk23Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk23Status === -1">
                  <span v-if="scope.row.wk23Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk23Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk23Msrp }}</span>
                 <b v-if="b231">+</b>
                            <b v-if="b232">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk23Status !== -1">
                  <span v-if="scope.row.wk23Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk23Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk23Msrp }}</span>
               <b v-if="b231">+</b>
                            <b v-if="b232">-</b>
                  </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK23-USD" prop="wk23MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk23Status === -1">
            <span v-if="scope.row.wk23MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk23Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk23MsrpUSD }}</span>
               <b v-if="b231">+</b>
                            <b v-if="b232">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk23Status !== -1">
                <span v-if="scope.row.wk23MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk23Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk23MsrpUSD }}</span>
                   <b v-if="b231">+</b>
                            <b v-if="b232">-</b>
               </span>
                <span v-else-if="scope.row.wk23Msrp !== null">
                          <span @click="showWk23Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk23Msrp }}</span>
                           <b v-if="b231">+</b>
                            <b v-if="b232">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk23" header-align="center" :label="a231" prop="wk23Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk23Day1Status === -1">
                  <span v-if="scope.row.wk23Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk23Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk23Day1Status !== -1">
                  <span v-if="scope.row.wk23Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk23Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk23Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk23Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk23" header-align="center" :label="a232" prop="wk23Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk23Day2Status === -1">
                  <span v-if="scope.row.wk23Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk23Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk23Day2Status !== -1">
                  <span v-if="scope.row.wk23Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk23Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk23Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk23Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk23" header-align="center" :label="a233" prop="wk23Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk23Day3Status === -1">
                  <span v-if="scope.row.wk23Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk23Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk23Day3Status !== -1">
                  <span v-if="scope.row.wk23Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk23Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk23Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk23Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk23" header-align="center" :label="a234" prop="wk23Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk23Day4Status === -1">
                  <span v-if="scope.row.wk23Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk23Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk23Day4Status !== -1">
                  <span v-if="scope.row.wk23Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk23Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk23Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk23Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk23" header-align="center" :label="a235" prop="wk23Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk23Day5Status === -1">
                  <span v-if="scope.row.wk23Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk23Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk23Day5Status !== -1">
                  <span v-if="scope.row.wk23Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk23Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk23Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk23Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk23" header-align="center" :label="a236" prop="wk23Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk23Day6Status === -1">
                  <span v-if="scope.row.wk23Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk23Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk23Day6Status !== -1">
                  <span v-if="scope.row.wk23Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk23Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk23Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk23Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk23" header-align="center" :label="a237" prop="wk23Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk23Day7Status === -1">
                  <span v-if="scope.row.wk23Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk23Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk23Day7Status !== -1">
                  <span v-if="scope.row.wk23Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk23Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk23Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk23Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK24-Country" prop="wk24Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk24Status === -1">
                  <span v-if="scope.row.wk24Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk24Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk24Msrp }}</span>
               <b v-if="b241">+</b>
                            <b v-if="b242">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk24Status !== -1">
                  <span v-if="scope.row.wk24Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk24Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk24Msrp }}</span>
               <b v-if="b241">+</b>
                            <b v-if="b242">-</b>
                  </span>
                   <!-- <span v-else-if="scope.row.wk24Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk24Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK24-USD" prop="wk24MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk24Status === -1">
            <span v-if="scope.row.wk24MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk24Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk24MsrpUSD }}</span>
               <b v-if="b241">+</b>
                            <b v-if="b242">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk24Status !== -1">
                <span v-if="scope.row.wk24MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk24Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk24MsrpUSD }}</span>
                   <b v-if="b241">+</b>
                            <b v-if="b242">-</b>
               </span>
                <span v-else-if="scope.row.wk24Msrp !== null">
                          <span @click="showWk24Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk24Msrp }}</span>
                           <b v-if="b241">+</b>
                            <b v-if="b242">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk24" header-align="center" :label="a241" prop="wk24Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk24Day1Status === -1">
                  <span v-if="scope.row.wk24Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk24Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk24Day1Status !== -1">
                  <span v-if="scope.row.wk24Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk24Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk24Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk24Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk24" header-align="center" :label="a242" prop="wk24Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk24Day2Status === -1">
                  <span v-if="scope.row.wk24Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk24Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk24Day2Status !== -1">
                  <span v-if="scope.row.wk24Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk24Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk24Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk24Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk24" header-align="center" :label="a243" prop="wk24Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk24Day3Status === -1">
                  <span v-if="scope.row.wk24Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk24Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk24Day3Status !== -1">
                  <span v-if="scope.row.wk24Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk24Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk24Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk24Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk24" header-align="center" :label="a244" prop="wk24Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk24Day4Status === -1">
                  <span v-if="scope.row.wk24Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk24Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk24Day4Status !== -1">
                  <span v-if="scope.row.wk24Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk24Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk24Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk24Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk24" header-align="center" :label="a245" prop="wk24Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk24Day5Status === -1">
                  <span v-if="scope.row.wk24Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk24Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk24Day5Status !== -1">
                  <span v-if="scope.row.wk24Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk24Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk24Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk24Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk24" header-align="center" :label="a246" prop="wk24Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk24Day6Status === -1">
                  <span v-if="scope.row.wk24Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk24Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk24Day6Status !== -1">
                  <span v-if="scope.row.wk24Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk24Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk24Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk24Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk24" header-align="center" :label="a247" prop="wk24Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk24Day7Status === -1">
                  <span v-if="scope.row.wk24Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk24Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk24Day7Status !== -1">
                  <span v-if="scope.row.wk24Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk24Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk24Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk24Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK25-Country" prop="wk25Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk25Status === -1">
                  <span v-if="scope.row.wk25Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk25Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk25Msrp }}</span>
               <b v-if="b251">+</b>
                            <b v-if="b252">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk25Status !== -1">
                  <span v-if="scope.row.wk25Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk25Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk25Msrp }}</span>
               <b v-if="b251">+</b>
                            <b v-if="b252">-</b>
                  </span>
                   <!-- <span v-else-if="scope.row.wk25Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk25Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK25-USD" prop="wk25MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk25Status === -1">
            <span v-if="scope.row.wk25MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk25Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk25MsrpUSD }}</span>
               <b v-if="b251">+</b>
                            <b v-if="b252">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk25Status !== -1">
                <span v-if="scope.row.wk25MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk25Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk25MsrpUSD }}</span>
                   <b v-if="b251">+</b>
                            <b v-if="b252">-</b>
               </span>
                <span v-else-if="scope.row.wk25Msrp !== null">
                          <span @click="showWk25Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk25Msrp }}</span>
                           <b v-if="b251">+</b>
                            <b v-if="b252">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk25" header-align="center" :label="a251" prop="wk25Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk25Day1Status === -1">
                  <span v-if="scope.row.wk25Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk25Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk25Day1Status !== -1">
                  <span v-if="scope.row.wk25Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk25Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk25Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk25Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk25" header-align="center" :label="a252" prop="wk25Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk25Day2Status === -1">
                  <span v-if="scope.row.wk25Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk25Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk25Day2Status !== -1">
                  <span v-if="scope.row.wk25Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk25Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk25Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk25Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk25" header-align="center" :label="a253" prop="wk25Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk25Day3Status === -1">
                  <span v-if="scope.row.wk25Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk25Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk25Day3Status !== -1">
                  <span v-if="scope.row.wk25Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk25Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk25Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk25Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk25" header-align="center" :label="a254" prop="wk25Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk25Day4Status === -1">
                  <span v-if="scope.row.wk25Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk25Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk25Day4Status !== -1">
                  <span v-if="scope.row.wk25Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk25Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk25Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk25Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk25" header-align="center" :label="a255" prop="wk25Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk25Day5Status === -1">
                  <span v-if="scope.row.wk25Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk25Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk25Day5Status !== -1">
                  <span v-if="scope.row.wk25Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk25Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk25Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk25Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk25" header-align="center" :label="a256" prop="wk25Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk25Day6Status === -1">
                  <span v-if="scope.row.wk25Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk25Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk25Day6Status !== -1">
                  <span v-if="scope.row.wk25Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk25Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk25Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk25Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk25" header-align="center" :label="a257" prop="wk25Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk25Day7Status === -1">
                  <span v-if="scope.row.wk25Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk25Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk25Day7Status !== -1">
                  <span v-if="scope.row.wk25Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk25Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk25Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk25Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK26-Country" prop="wk26Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk26Status === -1">
                  <span v-if="scope.row.wk26Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk26Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk26Msrp }}</span>
               <b v-if="b261">+</b>
                            <b v-if="b262">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk26Status !== -1">
                  <span v-if="scope.row.wk26Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk26Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk26Msrp }}</span>
               <b v-if="b261">+</b>
                            <b v-if="b262">-</b>
                  </span>
                   <!-- <span v-else-if="scope.row.wk26Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk26Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK26-USD" prop="wk26MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk26Status === -1">
            <span v-if="scope.row.wk26MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk26Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk26MsrpUSD }}</span>
               <b v-if="b261">+</b>
                            <b v-if="b262">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk26Status !== -1">
                <span v-if="scope.row.wk26MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk26Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk26MsrpUSD }}</span>
                   <b v-if="b261">+</b>
                            <b v-if="b262">-</b>
               </span>
                <span v-else-if="scope.row.wk26Msrp !== null">
                          <span @click="showWk26Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk26Msrp }}</span>
                           <b v-if="b261">+</b>
                            <b v-if="b262">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk26" header-align="center" :label="a261" prop="wk26Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk26Day1Status === -1">
                  <span v-if="scope.row.wk26Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk26Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk26Day1Status !== -1">
                  <span v-if="scope.row.wk26Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk26Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk26Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk26Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk26" header-align="center" :label="a262" prop="wk26Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk26Day2Status === -1">
                  <span v-if="scope.row.wk26Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk26Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk26Day2Status !== -1">
                  <span v-if="scope.row.wk26Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk26Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk26Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk26Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk26" header-align="center" :label="a263" prop="wk26Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk26Day3Status === -1">
                  <span v-if="scope.row.wk26Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk26Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk26Day3Status !== -1">
                  <span v-if="scope.row.wk26Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk26Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk26Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk26Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk26" header-align="center" :label="a264" prop="wk26Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk26Day4Status === -1">
                  <span v-if="scope.row.wk26Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk26Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk26Day4Status !== -1">
                  <span v-if="scope.row.wk26Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk26Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk26Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk26Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk26" header-align="center" :label="a265" prop="wk26Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk26Day5Status === -1">
                  <span v-if="scope.row.wk26Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk26Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk26Day5Status !== -1">
                  <span v-if="scope.row.wk26Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk26Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk26Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk26Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk26" header-align="center" :label="a266" prop="wk26Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk26Day6Status === -1">
                  <span v-if="scope.row.wk26Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk26Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk26Day6Status !== -1">
                  <span v-if="scope.row.wk26Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk26Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk26Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk26Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk26" header-align="center" :label="a267" prop="wk26Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk26Day7Status === -1">
                  <span v-if="scope.row.wk26Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk26Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk26Day7Status !== -1">
                  <span v-if="scope.row.wk26Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk26Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk26Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk26Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK27-Country" prop="wk27Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk27tatus === -1">
                  <span v-if="scope.row.wk27Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk27Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk27Msrp }}</span>
               <b v-if="b271">+</b>
                            <b v-if="b272">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk27Status !== -1">
                  <span v-if="scope.row.wk27Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk27Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk27Msrp }}</span>
                  </span>
                   <!-- <span v-else-if="scope.row.wk27Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk27Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
         <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK27-USD" prop="wk27MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk27tatus === -1">
                  <span v-if="scope.row.wk27MsrpUSD !== null">
                <!-- <b>$</b> -->
              <span @click="showWk27Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk27MsrpUSD }}</span>
               <b v-if="b271">+</b>
                            <b v-if="b272">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk27tatus !== -1">
                  <span v-if="scope.row.wk27MsrpUSD !== null">
                <!-- <b>$</b> -->
              <span @click="showWk27Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk27MsrpUSD }}</span>
               <b v-if="b271">+</b>
                            <b v-if="b272">-</b>
                  </span>
                   <span v-else-if="scope.row.wk27Msrp !== null">
                          <span @click="showWk27Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk27Msrp }}</span>
                           <b v-if="b271">+</b>
                            <b v-if="b272">-</b>
                        </span>
              </span>   
          </template>
      </el-table-column>
          <el-table-column :key="Math.random()" v-if="showWk27" header-align="center" :label="a271" prop="wk27Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk27Day1Status === -1">
                  <span v-if="scope.row.wk27Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk27Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk27Day1Status !== -1">
                  <span v-if="scope.row.wk27Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk27Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk27Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk27Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk27" header-align="center" :label="a272" prop="wk27Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk27Day2Status === -1">
                  <span v-if="scope.row.wk27Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk27Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk27Day2Status !== -1">
                  <span v-if="scope.row.wk27Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk27Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk27Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk27Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk27" header-align="center" :label="a273" prop="wk27Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk27Day3Status === -1">
                  <span v-if="scope.row.wk27Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk27Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk27Day3Status !== -1">
                  <span v-if="scope.row.wk27Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk27Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk27Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk27Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk27" header-align="center" :label="a274" prop="wk27Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk27Day4Status === -1">
                  <span v-if="scope.row.wk27Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk27Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk27Day4Status !== -1">
                  <span v-if="scope.row.wk27Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk27Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk27Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk27Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk27" header-align="center" :label="a275" prop="wk27Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk27Day5Status === -1">
                  <span v-if="scope.row.wk27Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk27Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk27Day5Status !== -1">
                  <span v-if="scope.row.wk27Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk27Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk27Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk27Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk27" header-align="center" :label="a276" prop="wk27Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk27Day6Status === -1">
                  <span v-if="scope.row.wk27Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk27Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk27Day6Status !== -1">
                  <span v-if="scope.row.wk27Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk27Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk27Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk27Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk27" header-align="center" :label="a277" prop="wk27Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk27Day7Status === -1">
                  <span v-if="scope.row.wk27Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk27Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk27Day7Status !== -1">
                  <span v-if="scope.row.wk27Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk27Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk27Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk27Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK28-Country" prop="wk28Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk28Status === -1">
                  <span v-if="scope.row.wk28Msrp !== null">
              <span @click="showWk28Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk28Msrp }}</span>
                    <b v-if="b281">+</b>
                    <b v-if="b282">-</b>
                  </span>
                  
              </span>   
              <span v-else-if="scope.row.wk28Status !== -1">
                  <span v-if="scope.row.wk28Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk28Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk28Msrp }}</span>
                  <b v-if="b281">+</b>
                    <b v-if="b282">-</b>
                  </span>
                  
                   <!-- <span v-else-if="scope.row.wk28Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk28Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
         <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK28-USD" prop="wk28MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk28Status === -1">
                  <span v-if="scope.row.wk28MsrpUSD !== null">
                <!-- <b>$</b> -->
              <span @click="showWk28Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk28MsrpUSD }}</span>
                <b v-if="b281">+</b>
                    <b v-if="b282">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk28Status !== -1">
                  <span v-if="scope.row.wk28MsrpUSD !== null">
                <!-- <b>$</b> -->
              <span @click="showWk28Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk28MsrpUSD }}</span>
                <b v-if="b281">+</b>
                    <b v-if="b282">-</b>
                  </span>
                   <span v-else-if="scope.row.wk28Msrp !== null">
                          <span @click="showWk28Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk28Msrp }}</span>
                            <b v-if="b281">+</b>
                    <b v-if="b282">-</b>
                        </span>
              </span>   
          </template>
      </el-table-column>
          <el-table-column :key="Math.random()" v-if="showWk28" header-align="center" :label="a281" prop="wk28Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk28Day1Status === -1">
                  <span v-if="scope.row.wk28Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk28Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk28Day1Status !== -1">
                  <span v-if="scope.row.wk28Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk28Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk28Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk28Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk28" header-align="center" :label="a282" prop="wk28Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk28Day2Status === -1">
                  <span v-if="scope.row.wk28Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk28Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk28Day2Status !== -1">
                  <span v-if="scope.row.wk28Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk28Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk28Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk28Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk28" header-align="center" :label="a283" prop="wk28Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk28Day3Status === -1">
                  <span v-if="scope.row.wk28Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk28Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk28Day3Status !== -1">
                  <span v-if="scope.row.wk28Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk28Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk28Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk28Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk28" header-align="center" :label="a284" prop="wk28Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk28Day4Status === -1">
                  <span v-if="scope.row.wk28Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk28Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk28Day4Status !== -1">
                  <span v-if="scope.row.wk28Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk28Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk28Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk28Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk28" header-align="center" :label="a285" prop="wk28Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk28Day5Status === -1">
                  <span v-if="scope.row.wk28Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk28Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk28Day5Status !== -1">
                  <span v-if="scope.row.wk28Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk28Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk28Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk28Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk28" header-align="center" :label="a286" prop="wk28Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk28Day6Status === -1">
                  <span v-if="scope.row.wk28Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk28Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk28Day6Status !== -1">
                  <span v-if="scope.row.wk28Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk28Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk28Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk28Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk28" header-align="center" :label="a287" prop="wk28Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk28Day7Status === -1">
                  <span v-if="scope.row.wk28Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk28Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk28Day7Status !== -1">
                  <span v-if="scope.row.wk28Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk28Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk28Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk28Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK29-Country" prop="wk29Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk29Status === -1">
                  <span v-if="scope.row.wk29Msrp !== null">
                    <span @click="showWk29Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk29Msrp }}</span>
                      <b v-if="b291">+</b>
                      <b v-if="b292">-</b>
                  </span>
                    
              </span>   
              <span v-else-if="scope.row.wk29Status !== -1">
                  <span v-if="scope.row.wk29Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk29Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk29Msrp }}</span>
                      <b v-if="b291">+</b>
                      <b v-if="b292">-</b>
                  </span>
                  
                   <!-- <span v-else-if="scope.row.wk29Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk29Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
          <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK29-USD" prop="wk29MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk29Status === -1">
                  <span v-if="scope.row.wk29MsrpUSD !== null">
                <!-- <b>$</b> -->
              <span @click="showWk29Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk29MsrpUSD }}</span>
                      <b v-if="b291">+</b>
                      <b v-if="b292">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk29Status !== -1">
                  <span v-if="scope.row.wk29MsrpUSD !== null">
                <!-- <b>$</b> -->
              <span @click="showWk29Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk29MsrpUSD }}</span>
                      <b v-if="b291">+</b>
                      <b v-if="b292">-</b>
                  </span>
                   <span v-else-if="scope.row.wk29Msrp !== null">
                          <span @click="showWk29Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk29Msrp }}</span>
                            <b v-if="b291">+</b>
                            <b v-if="b292">-</b>
                        </span>
              </span>   
          </template>
      </el-table-column>

            <el-table-column :key="Math.random()" v-if="showWk29" header-align="center" :label="a291" prop="wk29Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk29Day1Status === -1">
                  <span v-if="scope.row.wk29Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk29Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk29Day1Status !== -1">
                  <span v-if="scope.row.wk29Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk29Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk29Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk29Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk29" header-align="center" :label="a292" prop="wk29Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk29Day2Status === -1">
                  <span v-if="scope.row.wk29Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk29Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk29Day2Status !== -1">
                  <span v-if="scope.row.wk29Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk29Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk29Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk29Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk29" header-align="center" :label="a293" prop="wk29Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk29Day3Status === -1">
                  <span v-if="scope.row.wk29Day3 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk29Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk29Day3Status !== -1">
                  <span v-if="scope.row.wk29Day3 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk29Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk29Day3 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk29Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk29" header-align="center" :label="a294" prop="wk29Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk29Day4Status === -1">
                  <span v-if="scope.row.wk29Day4 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk29Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk29Day4Status !== -1">
                  <span v-if="scope.row.wk29Day4 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk29Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk29Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk29Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk29" header-align="center" :label="a295" prop="wk29Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk29Day5Status === -1">
                  <span v-if="scope.row.wk29Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk29Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk29Day5Status !== -1">
                  <span v-if="scope.row.wk29Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk29Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk29Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk29Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk29" header-align="center" :label="a296" prop="wk29Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk29Day6Status === -1">
                  <span v-if="scope.row.wk29Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk29Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk29Status !== -1">
                  <span v-if="scope.row.wk29Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk29Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk29Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk29Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk29" header-align="center" :label="a297" prop="wk29Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk29Day7Status === -1">
                  <span v-if="scope.row.wk29Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk29Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk29Day7Status !== -1">
                  <span v-if="scope.row.wk29Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk29Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk29Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk29Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>



      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK30-Country" prop="wk30Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk30Status === -1">
                  <span v-if="scope.row.wk30Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk30Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk30Msrp }}</span>
                    <b v-if="b301">+</b>
                      <b v-if="b302">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk30Status !== -1">
                  <span v-if="scope.row.wk30Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk30Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk30Msrp }}</span>
              <b v-if="b301">+</b>
                      <b v-if="b302">-</b>
                  </span>
                   <!-- <span v-else-if="scope.row.wk30Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk30Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK30-USD" prop="wk30MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk30Status === -1">
            <span v-if="scope.row.wk30MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk30Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk30MsrpUSD }}</span>
              <b v-if="b301">+</b>
                      <b v-if="b302">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk30Status !== -1">
                <span v-if="scope.row.wk30MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk30Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk30MsrpUSD }}</span>
                  <b v-if="b301">+</b>
                      <b v-if="b302">-</b>
               </span>
                <span v-else-if="scope.row.wk30Msrp !== null">
                          <span @click="showWk30Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk30Msrp }}</span>
                          <b v-if="b301">+</b>
                      <b v-if="b302">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk30" header-align="center" :label="a301" prop="wk30Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk30Day1Status === -1">
                  <span v-if="scope.row.wk30Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk30Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk30Day1Status !== -1">
                  <span v-if="scope.row.wk30Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk30Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk30Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk30Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk30" header-align="center" :label="a302" prop="wk30Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk30Day2Status === -1">
                  <span v-if="scope.row.wk30Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk30Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk30Day2Status !== -1">
                  <span v-if="scope.row.wk30Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk30Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk30Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk30Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk30" header-align="center" :label="a303" prop="wk30Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk30Day3Status === -1">
                  <span v-if="scope.row.wk30Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk30Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk30Day3Status !== -1">
                  <span v-if="scope.row.wk30Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk30Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk30Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk30Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk30" header-align="center" :label="a304" prop="wk30Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk30Day4Status === -1">
                  <span v-if="scope.row.wk30Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk30Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk30Day4Status !== -1">
                  <span v-if="scope.row.wk30Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk30Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk30Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk30Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk30" header-align="center" :label="a305" prop="wk30Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk30Day5Status === -1">
                  <span v-if="scope.row.wk30Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk30Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk30Day5Status !== -1">
                  <span v-if="scope.row.wk30Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk30Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk30Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk30Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk30" header-align="center" :label="a306" prop="wk30Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk30Day6Status === -1">
                  <span v-if="scope.row.wk30Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk30Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk30Day6Status !== -1">
                  <span v-if="scope.row.wk30Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk30Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk30Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk30Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk30" header-align="center" :label="a307" prop="wk30Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk30Day7Status === -1">
                  <span v-if="scope.row.wk30Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk30Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk30Day7Status !== -1">
                  <span v-if="scope.row.wk30Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk30Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk1Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk30Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK31-Country" prop="wk31Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk31Status === -1">
                  <span v-if="scope.row.wk31Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk31Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk31Msrp }}</span>
                    <b v-if="b311">+</b>
                      <b v-if="b312">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk31Status !== -1">
                  <span v-if="scope.row.wk31Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk31Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk31Msrp }}</span>
                   <b v-if="b311">+</b>
                      <b v-if="b312">-</b>
                  </span>
                   <!-- <span v-else-if="scope.row.wk31Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk31Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK31-USD" prop="wk31MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk31Status === -1">
            <span v-if="scope.row.wk31MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk31Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk31MsrpUSD }}</span>
              <b v-if="b311">+</b>
                      <b v-if="b312">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk31Status !== -1">
                <span v-if="scope.row.wk31MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk31Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk31MsrpUSD }}</span>
                  <b v-if="b311">+</b>
                      <b v-if="b312">-</b>
               </span>
                <span v-else-if="scope.row.wk31Msrp !== null">
                          <span @click="showWk31Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk31Msrp }}</span>
                          <b v-if="b311">+</b>
                      <b v-if="b312">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk31" header-align="center" :label="a311" prop="wk31Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk31Day1Status === -1">
                  <span v-if="scope.row.wk31Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk31Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk31Day1Status !== -1">
                  <span v-if="scope.row.wk31Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk31Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk31Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk31Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk31" header-align="center" :label="a312" prop="wk31Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk31Day2Status === -1">
                  <span v-if="scope.row.wk31Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk31Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk31Day2Status !== -1">
                  <span v-if="scope.row.wk31Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk31Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk31Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk31Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk31" header-align="center" :label="a313" prop="wk31Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk31Day3Status === -1">
                  <span v-if="scope.row.wk31Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk31Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk31Day3Status !== -1">
                  <span v-if="scope.row.wk31Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk31Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk31Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk31Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk31" header-align="center" :label="a314" prop="wk31Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk31Day4Status === -1">
                  <span v-if="scope.row.wk31Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk31Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk31Day4Status !== -1">
                  <span v-if="scope.row.wk31Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk31Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk31Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk31Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk31" header-align="center" :label="a315" prop="wk31Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk31Day5Status === -1">
                  <span v-if="scope.row.wk31Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk31Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk31Day5Status !== -1">
                  <span v-if="scope.row.wk31Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk31Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk31Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk31Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk31" header-align="center" :label="a316" prop="wk31Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk31Day6Status === -1">
                  <span v-if="scope.row.wk31Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk31Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk31Day6Status !== -1">
                  <span v-if="scope.row.wk31Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk31Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk31Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk31Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk31" header-align="center" :label="a317" prop="wk31Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk31Day7Status === -1">
                  <span v-if="scope.row.wk31Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk31Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk31Day7Status !== -1">
                  <span v-if="scope.row.wk31Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk31Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk31Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk31Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK32-Country" prop="wk32Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk32Status === -1">
                  <span v-if="scope.row.wk32Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk32Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk32Msrp }}</span>
                <b v-if="b321">+</b>
                      <b v-if="b322">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk32Status !== -1">
                  <span v-if="scope.row.wk32Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk32Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk32Msrp }}</span>
                       <b v-if="b321">+</b>
                      <b v-if="b322">-</b>
                  </span>
                   <!-- <span v-else-if="scope.row.wk32Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk32Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK32-USD" prop="wk32MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk32Status === -1">
            <span v-if="scope.row.wk32MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk32Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk32MsrpUSD }}</span>
                  <b v-if="b321">+</b>
                      <b v-if="b322">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk32Status !== -1">
                <span v-if="scope.row.wk32MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk32Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk32MsrpUSD }}</span>
                      <b v-if="b321">+</b>
                      <b v-if="b322">-</b>
               </span>
                <span v-else-if="scope.row.wk32Msrp !== null">
                          <span @click="showWk32Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk32Msrp }}</span>
                             <b v-if="b321">+</b>
                      <b v-if="b322">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk32" header-align="center" :label="a321" prop="wk32Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk32Day1Status === -1">
                  <span v-if="scope.row.wk32Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk32Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk32Day1Status !== -1">
                  <span v-if="scope.row.wk32Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk32Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk32Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk32Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk32" header-align="center" :label="a322" prop="wk32Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk32Day2Status === -1">
                  <span v-if="scope.row.wk32Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk32Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk32Day2Status !== -1">
                  <span v-if="scope.row.wk32Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk32Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk32Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk32Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk32" header-align="center" :label="a323" prop="wk32Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk32Day3Status === -1">
                  <span v-if="scope.row.wk32Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk32Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk32Day3Status !== -1">
                  <span v-if="scope.row.wk32Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk32Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk32Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk32Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk32" header-align="center" :label="a324" prop="wk32Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk32Day4Status === -1">
                  <span v-if="scope.row.wk32Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk32Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk32Day4Status !== -1">
                  <span v-if="scope.row.wk32Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk32Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk32Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk32Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk32" header-align="center" :label="a325" prop="wk32Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk32Day5Status === -1">
                  <span v-if="scope.row.wk32Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk32Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk32Day5Status !== -1">
                  <span v-if="scope.row.wk32Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk32Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk32Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk32Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk32" header-align="center" :label="a326" prop="wk32Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk32Day6Status === -1">
                  <span v-if="scope.row.wk32Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk32Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk32Day6Status !== -1">
                  <span v-if="scope.row.wk32Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk32Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk32Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk32Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk32" header-align="center" :label="a327" prop="wk32Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk32Day7Status === -1">
                  <span v-if="scope.row.wk32Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk32Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk32Day7Status !== -1">
                  <span v-if="scope.row.wk32Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk32Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk32Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk32Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK33-Country" prop="wk33Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk33Status === -1">
                  <span v-if="scope.row.wk33Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk33Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk33Msrp }}</span>
                        <b v-if="b331">+</b>
                      <b v-if="b332">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk33Status !== -1">
                  <span v-if="scope.row.wk33Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk33Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk33Msrp }}</span>
                       <b v-if="b331">+</b>
                      <b v-if="b332">-</b>
                    </span>
                   <!-- <span v-else-if="scope.row.wk33Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk33Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK33-USD" prop="wk33MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk33Status === -1">
            <span v-if="scope.row.wk33MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk33Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk33MsrpUSD }}</span>
               <b v-if="b331">+</b>
                      <b v-if="b332">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk33Status !== -1">
                <span v-if="scope.row.wk33MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk33Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk33MsrpUSD }}</span>
                   <b v-if="b331">+</b>
                      <b v-if="b332">-</b>
               </span>
                <span v-else-if="scope.row.wk33Msrp !== null">
                          <span @click="showWk33Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk33Msrp }}</span>
                          <b v-if="b331">+</b>
                      <b v-if="b332">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk33" header-align="center" :label="a331" prop="wk33Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk33Day1Status === -1">
                  <span v-if="scope.row.wk33Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk33Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk33Day1Status !== -1">
                  <span v-if="scope.row.wk33Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk33Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk33Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk33Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk33" header-align="center" :label="a332" prop="wk33Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk33Day2Status === -1">
                  <span v-if="scope.row.wk33Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk33Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk33Day2Status !== -1">
                  <span v-if="scope.row.wk33Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk33Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk33Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk33Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk33" header-align="center" :label="a333" prop="wk33Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk33Day3Status === -1">
                  <span v-if="scope.row.wk33Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk33Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk33Day3Status !== -1">
                  <span v-if="scope.row.wk33Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk33Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk33Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk33Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk33" header-align="center" :label="a334" prop="wk33Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk33Day4Status === -1">
                  <span v-if="scope.row.wk33Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk33Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk33Day4Status !== -1">
                  <span v-if="scope.row.wk33Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk33Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk33Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk33Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk33" header-align="center" :label="a335" prop="wk33Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk33Day5Status === -1">
                  <span v-if="scope.row.wk33Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk33Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk33Day5Status !== -1">
                  <span v-if="scope.row.wk33Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk33Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk33Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk33Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk33" header-align="center" :label="a336" prop="wk33Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk33Day6Status === -1">
                  <span v-if="scope.row.wk33Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk33Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk33Day6Status !== -1">
                  <span v-if="scope.row.wk33Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk33Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk33Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk33Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk33" header-align="center" :label="a337" prop="wk33Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk33Day7Status === -1">
                  <span v-if="scope.row.wk33Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk33Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk33Day7Status !== -1">
                  <span v-if="scope.row.wk33Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk33Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk33Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk33Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK34-Country" prop="wk34Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk34Status === -1">
                  <span v-if="scope.row.wk34Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk34Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk34Msrp }}</span>
               <b v-if="b341">+</b>
                      <b v-if="b342">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk34Status !== -1">
                  <span v-if="scope.row.wk34Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk34Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk34Msrp }}</span>
               <b v-if="b341">+</b>
                      <b v-if="b342">-</b>
                  </span>
                   <!-- <span v-else-if="scope.row.wk34Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk34Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK34-USD" prop="wk34MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk34Status === -1">
            <span v-if="scope.row.wk34MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk34Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk34MsrpUSD }}</span>
               <b v-if="b341">+</b>
                      <b v-if="b342">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk34Status !== -1">
                <span v-if="scope.row.wk34MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk34Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk34MsrpUSD }}</span>
                   <b v-if="b341">+</b>
                      <b v-if="b342">-</b>
               </span>
                <span v-else-if="scope.row.wk34Msrp !== null">
                          <span @click="showWk34Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk34Msrp }}</span>
                           <b v-if="b341">+</b>
                      <b v-if="b342">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk34" header-align="center" :label="a341" prop="wk34Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk34Day1Status === -1">
                  <span v-if="scope.row.wk34Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk34Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk34Day1Status !== -1">
                  <span v-if="scope.row.wk34Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk34Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk34Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk34Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk34" header-align="center" :label="a342" prop="wk34Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk34Day2Status === -1">
                  <span v-if="scope.row.wk34Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk34Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk34Day2Status !== -1">
                  <span v-if="scope.row.wk34Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk34Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk34Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk34Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk34" header-align="center" :label="a343" prop="wk34Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk34Day3Status === -1">
                  <span v-if="scope.row.wk34Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk34Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk34Day3Status !== -1">
                  <span v-if="scope.row.wk34Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk34Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk34Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk34Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk34" header-align="center" :label="a344" prop="wk34Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk34Day4Status === -1">
                  <span v-if="scope.row.wk34Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk34Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk34Day4Status !== -1">
                  <span v-if="scope.row.wk34Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk34Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk34Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk34Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk34" header-align="center" :label="a345" prop="wk34Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk34Day5Status === -1">
                  <span v-if="scope.row.wk34Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk34Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk34Day5Status !== -1">
                  <span v-if="scope.row.wk34Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk34Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk34Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk34Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk34" header-align="center" :label="a346" prop="wk34Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk34Day6Status === -1">
                  <span v-if="scope.row.wk34Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk34Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk34Day6Status !== -1">
                  <span v-if="scope.row.wk34Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk34Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk34Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk34Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk34" header-align="center" :label="a347" prop="wk34Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk34Day7Status === -1">
                  <span v-if="scope.row.wk34Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk34Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk34Day7Status !== -1">
                  <span v-if="scope.row.wk34Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk34Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk34Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk34Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK35-Country" prop="wk35Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk35Status === -1">
                  <span v-if="scope.row.wk35Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk35Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk35Msrp }}</span>
               <b v-if="b351">+</b>
                      <b v-if="b352">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk35Status !== -1">
                  <span v-if="scope.row.wk35Msrp !== null">
                <!-- <b>$</b> -->
              <span  @click="showWk35Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk35Msrp }}</span>
                <b v-if="b351">+</b>
                      <b v-if="b352">-</b>
                  </span>
                   <!-- <span v-else-if="scope.row.wk35Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk35Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK35-USD" prop="wk35MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk35Status === -1">
            <span v-if="scope.row.wk35MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span  @click="showWk35Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk35MsrpUSD }}</span>
                <b v-if="b351">+</b>
                      <b v-if="b352">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk35Status !== -1">
                <span v-if="scope.row.wk35MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span  @click="showWk35Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk35MsrpUSD }}</span>
                    <b v-if="b351">+</b>
                      <b v-if="b352">-</b>
               </span>
                <span v-else-if="scope.row.wk35Msrp !== null">
                          <span  @click="showWk35Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk35Msrp }}</span>
                            <b v-if="b351">+</b>
                      <b v-if="b352">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk35" header-align="center" :label="a351" prop="wk35Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk35Day1Status === -1">
                  <span v-if="scope.row.wk35Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk35Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk35Day1Status !== -1">
                  <span v-if="scope.row.wk35Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk35Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk35Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk35Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk35" header-align="center" :label="a352" prop="wk35Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk35Day2Status === -1">
                  <span v-if="scope.row.wk35Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk35Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk35Day2Status !== -1">
                  <span v-if="scope.row.wk35Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk35Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk35Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk35Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk35" header-align="center" :label="a353" prop="wk35Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk35Day3Status === -1">
                  <span v-if="scope.row.wk35Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk35Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk35Day3Status !== -1">
                  <span v-if="scope.row.wk35Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk35Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk35Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk35Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk35" header-align="center" :label="a354" prop="wk35Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk35Day4Status === -1">
                  <span v-if="scope.row.wk35Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk35Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk35Day4Status !== -1">
                  <span v-if="scope.row.wk35Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk35Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk35Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk35Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk35" header-align="center" :label="a355" prop="wk35Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk35Day5Status === -1">
                  <span v-if="scope.row.wk35Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk35Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk35Day5Status !== -1">
                  <span v-if="scope.row.wk35Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk35Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk1Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk35Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk35" header-align="center" :label="a356" prop="wk35Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk35Day6Status === -1">
                  <span v-if="scope.row.wk35Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk35Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk35Day6Status !== -1">
                  <span v-if="scope.row.wk35Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk35Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk35Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk35Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk35" header-align="center" :label="a357" prop="wk35Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk35Day7Status === -1">
                  <span v-if="scope.row.wk35Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk35Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk35Day7Status !== -1">
                  <span v-if="scope.row.wk35Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk35Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk35Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk35Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK36-Country" prop="wk36Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk36Status === -1">
                  <span v-if="scope.row.wk36Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk36Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk36Msrp }}</span>
                <b v-if="b361">+</b>
                      <b v-if="b362">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk36Status !== -1">
                  <span v-if="scope.row.wk36Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk36Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk36Msrp }}</span>
               <b v-if="b361">+</b>
                      <b v-if="b362">-</b>
                  </span>
                   <!-- <span v-else-if="scope.row.wk36Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk36Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK36-USD" prop="wk36MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk36Status === -1">
            <span v-if="scope.row.wk36MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk36Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk36MsrpUSD }}</span>
               <b v-if="b361">+</b>
                      <b v-if="b362">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk36Status !== -1">
                <span v-if="scope.row.wk36MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk36Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk36MsrpUSD }}</span>
                   <b v-if="b361">+</b>
                      <b v-if="b362">-</b>
               </span>
                <span v-else-if="scope.row.wk36Msrp !== null">
                          <span @click="showWk36Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk36Msrp }}</span>
                           <b v-if="b361">+</b>
                      <b v-if="b362">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk36" header-align="center" :label="a361" prop="wk36Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk36Day1Status === -1">
                  <span v-if="scope.row.wk36Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk36Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk36Day1Status !== -1">
                  <span v-if="scope.row.wk36Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk36Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk36Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk36Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk36" header-align="center" :label="a362" prop="wk36Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk36Day2Status === -1">
                  <span v-if="scope.row.wk36Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk36Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk36Day2Status !== -1">
                  <span v-if="scope.row.wk36Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk36Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk36Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk36Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk36" header-align="center" :label="a363" prop="wk36Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk36Day3Status === -1">
                  <span v-if="scope.row.wk36Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk36Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk36Day3Status !== -1">
                  <span v-if="scope.row.wk36Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk36Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk36Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk36Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk36" header-align="center" :label="a364" prop="wk36Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk36Day4Status === -1">
                  <span v-if="scope.row.wk36Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk36Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk36Day4Status !== -1">
                  <span v-if="scope.row.wk36Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk36Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk36Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk36Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk36" header-align="center" :label="a365" prop="wk36Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk36Day5Status === -1">
                  <span v-if="scope.row.wk36Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk36Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk36Day5Status !== -1">
                  <span v-if="scope.row.wk36Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk36Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk36Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk36Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk36" header-align="center" :label="a366" prop="wk36Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk36Day6Status === -1">
                  <span v-if="scope.row.wk36Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk36Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk36Day6Status !== -1">
                  <span v-if="scope.row.wk36Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk36Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk36Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk36Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk36" header-align="center" :label="a367" prop="wk36Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk36Day7Status === -1">
                  <span v-if="scope.row.wk36Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk36Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk36Day7Status !== -1">
                  <span v-if="scope.row.wk36Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk36Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk36Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk36Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK37-Country" prop="wk37Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk37Status === -1">
                  <span v-if="scope.row.wk37Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk37Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk37Msrp }}</span>
               <b v-if="b371">+</b>
                      <b v-if="b372">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk37Status !== -1">
                  <span v-if="scope.row.wk37Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk37Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk37Msrp }}</span>
                 <b v-if="b371">+</b>
                      <b v-if="b372">-</b>
                  </span>
                   <!-- <span v-else-if="scope.row.wk37Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk37Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK37-USD" prop="wk37MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk37Status === -1">
            <span v-if="scope.row.wk37MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk37Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk37MsrpUSD }}</span>
                 <b v-if="b371">+</b>
                      <b v-if="b372">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk37Status !== -1">
                <span v-if="scope.row.wk37MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk37Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk37MsrpUSD }}</span>
                     <b v-if="b371">+</b>
                      <b v-if="b372">-</b>
               </span>
                <span v-else-if="scope.row.wk37Msrp !== null">
                          <span @click="showWk37Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk37Msrp }}</span>
                             <b v-if="b371">+</b>
                      <b v-if="b372">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk37" header-align="center" :label="a371" prop="wk37Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk37Day1Status === -1">
                  <span v-if="scope.row.wk37Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk37Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk37Day1Status !== -1">
                  <span v-if="scope.row.wk37Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk37Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk37Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk37Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk37" header-align="center" :label="a372" prop="wk37Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk37Day2Status === -1">
                  <span v-if="scope.row.wk37Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk37Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk37Day2Status !== -1">
                  <span v-if="scope.row.wk37Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk37Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk37Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk37Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk37" header-align="center" :label="a373" prop="wk37Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk37Day3Status === -1">
                  <span v-if="scope.row.wk37Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk37Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk37Day3Status !== -1">
                  <span v-if="scope.row.wk37Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk37Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk37Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk37Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk37" header-align="center" :label="a374" prop="wk37Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk37Day4Status === -1">
                  <span v-if="scope.row.wk37Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk37Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk37Day4Status !== -1">
                  <span v-if="scope.row.wk37Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk37Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk37Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk37Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk37" header-align="center" :label="a375" prop="wk37Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk37Day5Status === -1">
                  <span v-if="scope.row.wk37Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk37Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk37Day5Status !== -1">
                  <span v-if="scope.row.wk37Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk37Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk37Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk37Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk37" header-align="center" :label="a376" prop="wk37Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk37Day6Status === -1">
                  <span v-if="scope.row.wk37Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk37Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk37Day6Status !== -1">
                  <span v-if="scope.row.wk37Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk37Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk37Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk37Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk37" header-align="center" :label="a377" prop="wk37Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk37Day7Status === -1">
                  <span v-if="scope.row.wk37Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk37Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk37Day7Status !== -1">
                  <span v-if="scope.row.wk37Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk37Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk37Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk37Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK38-Country" prop="wk38Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk38Status === -1">
                  <span v-if="scope.row.wk38Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk38Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk38Msrp }}</span>
                 <b v-if="b381">+</b>
                      <b v-if="b382">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk38Status !== -1">
                  <span v-if="scope.row.wk38Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk38Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk38Msrp }}</span>
                <b v-if="b381">+</b>
                      <b v-if="b382">-</b>
                  </span>
                   <!-- <span v-else-if="scope.row.wk38Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk38Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK38-USD" prop="wk38MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk38Status === -1">
            <span v-if="scope.row.wk38MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk38Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk38MsrpUSD }}</span>
                <b v-if="b381">+</b>
                      <b v-if="b382">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk38Status !== -1">
                <span v-if="scope.row.wk38MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk38Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk38MsrpUSD }}</span>
                    <b v-if="b381">+</b>
                      <b v-if="b382">-</b>
               </span>
                <span v-else-if="scope.row.wk38Msrp !== null">
                          <span @click="showWk38Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk38Msrp }}</span>
                            <b v-if="b381">+</b>
                      <b v-if="b382">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk38" header-align="center" :label="a381" prop="wk38Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk38Day1Status === -1">
                  <span v-if="scope.row.wk38Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk38Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk38Day1Status !== -1">
                  <span v-if="scope.row.wk38Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk38Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk38Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk38Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk38" header-align="center" :label="a382" prop="wk38Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk38Day2Status === -1">
                  <span v-if="scope.row.wk38Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk38Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk38Day2Status !== -1">
                  <span v-if="scope.row.wk38Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk38Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk38Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk38Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk38" header-align="center" :label="a383" prop="wk38Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk38Day3Status === -1">
                  <span v-if="scope.row.wk38Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk38Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk38Day3Status !== -1">
                  <span v-if="scope.row.wk38Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk38Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk38Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk38Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk38" header-align="center" :label="a384" prop="wk38Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk38Day4Status === -1">
                  <span v-if="scope.row.wk38Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk38Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk38Day4Status !== -1">
                  <span v-if="scope.row.wk38Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk38Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk38Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk38Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk38" header-align="center" :label="a385" prop="wk38Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk38Day5Status === -1">
                  <span v-if="scope.row.wk38Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk38Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk38Day5Status !== -1">
                  <span v-if="scope.row.wk38Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk38Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk38Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk38Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk38" header-align="center" :label="a386" prop="wk38Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk38Day6Status === -1">
                  <span v-if="scope.row.wk38Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk38Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk38Day6Status !== -1">
                  <span v-if="scope.row.wk38Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk38Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk38Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk38Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk38" header-align="center" :label="a387" prop="wk38Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk38Day7Status === -1">
                  <span v-if="scope.row.wk38Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk38Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk38Day7Status !== -1">
                  <span v-if="scope.row.wk38Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk38Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk38Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk38Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK39-Country" prop="wk39Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk39Status === -1">
                  <span v-if="scope.row.wk39Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk39Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk39Msrp }}</span>
                <b v-if="b391">+</b>
                      <b v-if="b392">-</b>
                  </span>
              </span>   
                    <span v-else-if="scope.row.wk39Status !== -1">
                        <span v-if="scope.row.wk39Msrp !== null">
                          <span @click="showWk39Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk39Msrp }}</span>
                           <b v-if="b391">+</b>
                      <b v-if="b392">-</b>
                        </span>
                         <!-- <span v-else-if="scope.row.wk39Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk39Msrp }}</span>
                        </span> -->
                    </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK39-USD" prop="wk39MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk39Status === -1">
            <span v-if="scope.row.wk39MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk39Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk39MsrpUSD }}</span>
               <b v-if="b391">+</b>
                      <b v-if="b392">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk39Status !== -1">
                <span v-if="scope.row.wk39MsrpUSD !== null">
                  <span @click="showWk39Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk39MsrpUSD }}</span>
                   <b v-if="b391">+</b>
                      <b v-if="b392">-</b>
               </span>
                         <span v-else-if="scope.row.wk39Msrp !== null">
                          <span @click="showWk39Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk39Msrp }}</span>
                           <b v-if="b391">+</b>
                      <b v-if="b392">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk39" header-align="center" :label="a391" prop="wk39Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk39Day1Status === -1">
                  <span v-if="scope.row.wk39Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk39Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk39Day1Status !== -1">
                  <span v-if="scope.row.wk39Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk39Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk39Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk39Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk39" header-align="center" :label="a392" prop="wk39Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk39Day2Status === -1">
                  <span v-if="scope.row.wk39Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk39Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk39Day2Status !== -1">
                  <span v-if="scope.row.wk39Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk39Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk39Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk39Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk39" header-align="center" :label="a393" prop="wk39Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk39Day3Status === -1">
                  <span v-if="scope.row.wk39Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk39Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk39Day3Status !== -1">
                  <span v-if="scope.row.wk39Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk39Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk39Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk39Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk39" header-align="center" :label="a394" prop="wk39Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk39Day4Status === -1">
                  <span v-if="scope.row.wk39Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk39Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk39Day4Status !== -1">
                  <span v-if="scope.row.wk39Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk39Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk39Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk39Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk39" header-align="center" :label="a395" prop="wk39Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk39Day5Status === -1">
                  <span v-if="scope.row.wk39Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk39Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk39Day5Status !== -1">
                  <span v-if="scope.row.wk39Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk39Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk39Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk39Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk39" header-align="center" :label="a396" prop="wk39Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk39Day6Status === -1">
                  <span v-if="scope.row.wk39Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk39Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk39Day6Status !== -1">
                  <span v-if="scope.row.wk39Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk39Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk39Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk39Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk39" header-align="center" :label="a397" prop="wk39Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk39Day7Status === -1">
                  <span v-if="scope.row.wk39Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk39Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk39Day7Status !== -1">
                  <span v-if="scope.row.wk39Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk39Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk39Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk39Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK40-Country" prop="wk40Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk40Status === -1">
                  <span v-if="scope.row.wk40Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk40Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk40Msrp }}</span>
               <b v-if="b401">+</b>
                      <b v-if="b402">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk40Status !== -1">
                  <span v-if="scope.row.wk40Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk40Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk40Msrp }}</span>
                   <b v-if="b401">+</b>
                      <b v-if="b402">-</b>
                  </span>
                   <!-- <span v-else-if="scope.row.wk40Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk40Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK40-USD" prop="wk40MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk40Status === -1">
            <span v-if="scope.row.wk40MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk40Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk40MsrpUSD }}</span>
                   <b v-if="b401">+</b>
                      <b v-if="b402">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk40Status !== -1">
                <span v-if="scope.row.wk40MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk40Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk40MsrpUSD }}</span>
                       <b v-if="b401">+</b>
                      <b v-if="b402">-</b>
               </span>
                <span v-else-if="scope.row.wk40Msrp !== null">
                          <span @click="showWk40Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk40Msrp }}</span>
                               <b v-if="b401">+</b>
                      <b v-if="b402">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk40" header-align="center" :label="a401" prop="wk40Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk40Day1Status === -1">
                  <span v-if="scope.row.wk40Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk40Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk40Day1Status !== -1">
                  <span v-if="scope.row.wk40Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk40Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk40Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk40Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk40" header-align="center" :label="a402" prop="wk40Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk40Day2Status === -1">
                  <span v-if="scope.row.wk40Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk40Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk40Day2Status !== -1">
                  <span v-if="scope.row.wk40Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk40Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk40Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk40Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk40" header-align="center" :label="a403" prop="wk40Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk40Day3Status === -1">
                  <span v-if="scope.row.wk40Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk40Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk40Day3Status !== -1">
                  <span v-if="scope.row.wk40Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk40Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk40Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk40Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk40" header-align="center" :label="a404" prop="wk40Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk40Day4Status === -1">
                  <span v-if="scope.row.wk40Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk40Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk40Day4Status !== -1">
                  <span v-if="scope.row.wk40Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk40Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk40Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk40Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk40" header-align="center" :label="a405" prop="wk40Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk40Day5Status === -1">
                  <span v-if="scope.row.wk40Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk40Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk40Day5Status !== -1">
                  <span v-if="scope.row.wk40Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk40Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk40Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk40Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk40" header-align="center" :label="a406" prop="wk40Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk40Day6Status === -1">
                  <span v-if="scope.row.wk40Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk40Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk40Day6Status !== -1">
                  <span v-if="scope.row.wk40Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk40Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk40Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk40Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk40" header-align="center" :label="a407" prop="wk40Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk40Day7Status === -1">
                  <span v-if="scope.row.wk40Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk40Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk40Day7Status !== -1">
                  <span v-if="scope.row.wk40Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk40Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk40Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk40Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK41-Country" prop="wk41Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk41Status === -1">
                  <span v-if="scope.row.wk41Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk41Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk41Msrp }}</span>
                   <b v-if="b411">+</b>
                      <b v-if="b412">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk41Status !== -1">
                  <span v-if="scope.row.wk41Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk41Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk41Msrp }}</span>
                <b v-if="b411">+</b>
                      <b v-if="b412">-</b>
                  </span>
                   <!-- <span v-else-if="scope.row.wk41Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk41Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK41-USD" prop="wk41MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk41Status === -1">
            
            <span v-if="scope.row.wk41MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk41Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk41MsrpUSD }}</span>
                <b v-if="b411">+</b>
                      <b v-if="b412">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk41Status !== -1">
                <span v-if="scope.row.wk41MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk41Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk41MsrpUSD }}</span>
                    <b v-if="b411">+</b>
                      <b v-if="b412">-</b>
               </span>
                <span v-else-if="scope.row.wk41Msrp !== null">
                          <span @click="showWk41Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk41Msrp }}</span>
                            <b v-if="b411">+</b>
                      <b v-if="b412">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk41" header-align="center" :label="a411" prop="wk41Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk41Day1Status === -1">
                  <span v-if="scope.row.wk41Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk41Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk41Day1Status !== -1">
                  <span v-if="scope.row.wk41Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk41Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk41Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk41Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk41" header-align="center" :label="a412" prop="wk41Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk41Day2Status === -1">
                  <span v-if="scope.row.wk41Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk41Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk41Day2Status !== -1">
                  <span v-if="scope.row.wk41Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk41Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk41Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk41Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk1" header-align="center" :label="a413" prop="wk41Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk41Day3Status === -1">
                  <span v-if="scope.row.wk41Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk41Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk41Day3Status !== -1">
                  <span v-if="scope.row.wk41Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk41Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk41Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk41Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk41" header-align="center" :label="a414" prop="wk41Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk41Day4Status === -1">
                  <span v-if="scope.row.wk41Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk1Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk41Day4Status !== -1">
                  <span v-if="scope.row.wk41Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk41Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk41Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk41Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk41" header-align="center" :label="a415" prop="wk41Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk41Day5Status === -1">
                  <span v-if="scope.row.wk41Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk41Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk41Day5Status !== -1">
                  <span v-if="scope.row.wk41Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk41Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk41Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk41Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk41" header-align="center" :label="a416" prop="wk41Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk41Day6Status === -1">
                  <span v-if="scope.row.wk41Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk41Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk41Day6Status !== -1">
                  <span v-if="scope.row.wk41Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk41Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk41Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk41Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk41" header-align="center" :label="a417" prop="wk41Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk41Day7Status === -1">
                  <span v-if="scope.row.wk41Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk41Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk41Day7Status !== -1">
                  <span v-if="scope.row.wk41Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk41Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk41Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk41Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK42-Country" prop="wk42Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk42Status === -1">
                  <span v-if="scope.row.wk42Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk42Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk42Msrp }}</span>
                <b v-if="b421">+</b>
                      <b v-if="b422">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk42Status !== -1">
                  <span v-if="scope.row.wk42Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk42Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk42Msrp }}</span>
               <b v-if="b421">+</b>
                      <b v-if="b422">-</b>
                  </span>
                   <!-- <span v-else-if="scope.row.wk42Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk42Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK42-USD" prop="wk42MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk42Status === -1">
            <span v-if="scope.row.wk42MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk42Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk42MsrpUSD }}</span>
               <b v-if="b421">+</b>
                      <b v-if="b422">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk42Status !== -1">
                <span v-if="scope.row.wk42MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk42Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk42MsrpUSD }}</span>
                   <b v-if="b421">+</b>
                      <b v-if="b422">-</b>
               </span>
                <span v-else-if="scope.row.wk42Msrp !== null">
                          <span @click="showWk42Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk42Msrp }}</span>
                           <b v-if="b421">+</b>
                      <b v-if="b422">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk42" header-align="center" :label="a421" prop="wk42Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk42Day1Status === -1">
                  <span v-if="scope.row.wk42Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk42Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk42Day1Status !== -1">
                  <span v-if="scope.row.wk42Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk42Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk42Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk42Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk42" header-align="center" :label="a422" prop="wk42Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk42Day2Status === -1">
                  <span v-if="scope.row.wk42Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk42Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk42Day2Status !== -1">
                  <span v-if="scope.row.wk42Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk42Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk42Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk42Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk42" header-align="center" :label="a423" prop="wk42Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk42Day3Status === -1">
                  <span v-if="scope.row.wk42Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk42Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk42Day3Status !== -1">
                  <span v-if="scope.row.wk42Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk42Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk42Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk42Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk42" header-align="center" :label="a424" prop="wk42Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk42Day4Status === -1">
                  <span v-if="scope.row.wk42Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk42Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk42Day4Status !== -1">
                  <span v-if="scope.row.wk42Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk42Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk42Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk42Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk42" header-align="center" :label="a425" prop="wk42Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk42Day5Status === -1">
                  <span v-if="scope.row.wk42Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk42Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk42Day5Status !== -1">
                  <span v-if="scope.row.wk42Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk42Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk42Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk42Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk42" header-align="center" :label="a426" prop="wk42Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk42Day6Status === -1">
                  <span v-if="scope.row.wk42Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk42Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk42Day6Status !== -1">
                  <span v-if="scope.row.wk42Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk42Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk42Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk42Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk42" header-align="center" :label="a427" prop="wk42Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk42Day7Status === -1">
                  <span v-if="scope.row.wk42Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk42Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk42Day7Status !== -1">
                  <span v-if="scope.row.wk42Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk42Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk42Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk42Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK43-Country" prop="wk43Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk43Status === -1">
                  <span v-if="scope.row.wk43Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk43Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk43Msrp }}</span>
               <b v-if="b431">+</b>
                      <b v-if="b432">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk43Status !== -1">
                  <span v-if="scope.row.wk43Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk43Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk43Msrp }}</span>
                 <b v-if="b431">+</b>
                      <b v-if="b432">-</b>
                  </span>
                   <!-- <span v-else-if="scope.row.wk43Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk43Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK43-USD" prop="wk43MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk43Status === -1">
            <span v-if="scope.row.wk43MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk43Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk43MsrpUSD }}</span>
                 <b v-if="b431">+</b>
                      <b v-if="b432">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk43Status !== -1">
                <span v-if="scope.row.wk43MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk43Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk43MsrpUSD }}</span>
                     <b v-if="b431">+</b>
                      <b v-if="b432">-</b>
               </span>
                <span v-else-if="scope.row.wk43Msrp !== null">
                          <span @click="showWk43Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk43Msrp }}</span>
                             <b v-if="b431">+</b>
                      <b v-if="b432">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk43" header-align="center" :label="a431" prop="wk43Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk43Day1Status === -1">
                  <span v-if="scope.row.wk43Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk43Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk43Day1Status !== -1">
                  <span v-if="scope.row.wk43Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk43Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk43Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk43Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk43" header-align="center" :label="a432" prop="wk43Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk43Day2Status === -1">
                  <span v-if="scope.row.wk43Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk43Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk43Day2Status !== -1">
                  <span v-if="scope.row.wk43Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk43Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk43Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk43Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk43" header-align="center" :label="a433" prop="wk43Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk43Day3Status === -1">
                  <span v-if="scope.row.wk43Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk43Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk43Day3Status !== -1">
                  <span v-if="scope.row.wk43Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk43Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk43Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk43Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk43" header-align="center" :label="a434" prop="wk43Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk43Day4Status === -1">
                  <span v-if="scope.row.wk43Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk43Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk43Day4Status !== -1">
                  <span v-if="scope.row.wk43Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk43Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk43Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk43Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk43" header-align="center" :label="a435" prop="wk43Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk43Day5Status === -1">
                  <span v-if="scope.row.wk43Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk43Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk43Day5Status !== -1">
                  <span v-if="scope.row.wk43Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk43Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk43Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk43Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk43" header-align="center" :label="a436" prop="wk43Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk43Day6Status === -1">
                  <span v-if="scope.row.wk43Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk43Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk43Day6Status !== -1">
                  <span v-if="scope.row.wk43Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk43Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk43Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk43Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk43" header-align="center" :label="a437" prop="wk43Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk43Day7Status === -1">
                  <span v-if="scope.row.wk43Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk43Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk43Day7Status !== -1">
                  <span v-if="scope.row.wk43Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk43Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk43Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk43Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK44-Country" prop="wk44Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk44Status === -1">
                  <span v-if="scope.row.wk44Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk44Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk44Msrp }}</span>
                 <b v-if="b441">+</b>
                      <b v-if="b442">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk44Status !== -1">
                  <span v-if="scope.row.wk44Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk44Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk44Msrp }}</span>
               <b v-if="b441">+</b>
                      <b v-if="b442">-</b>
                  </span>
                   <!-- <span v-else-if="scope.row.wk44Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk44Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK44-USD" prop="wk44MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk44Status === -1">
            <span v-if="scope.row.wk44MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk44Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk44MsrpUSD }}</span>
               <b v-if="b441">+</b>
                      <b v-if="b442">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk44Status !== -1">
                <span v-if="scope.row.wk44MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk44Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk44MsrpUSD }}</span>
                   <b v-if="b441">+</b>
                      <b v-if="b442">-</b>
               </span>
                <span v-else-if="scope.row.wk44Msrp !== null">
                          <span @click="showWk44Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk44Msrp }}</span>
                           <b v-if="b441">+</b>
                      <b v-if="b442">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk44" header-align="center" :label="a441" prop="wk44Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk44Day1Status === -1">
                  <span v-if="scope.row.wk44Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk44Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk44Day1Status !== -1">
                  <span v-if="scope.row.wk44Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk44Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk44Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk44Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk44" header-align="center" :label="a442" prop="wk44Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk44Day2Status === -1">
                  <span v-if="scope.row.wk44Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk44Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk44Day2Status !== -1">
                  <span v-if="scope.row.wk44Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk44Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk1Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk44Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk44" header-align="center" :label="a443" prop="wk44Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk44Day3Status === -1">
                  <span v-if="scope.row.wk44Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk44Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk44Day3Status !== -1">
                  <span v-if="scope.row.wk44Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk44Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk44Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk44Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk44" header-align="center" :label="a444" prop="wk44Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk44Day4Status === -1">
                  <span v-if="scope.row.wk44Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk44Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk44Day4Status !== -1">
                  <span v-if="scope.row.wk44Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk44Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk44Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk44Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk44" header-align="center" :label="a445" prop="wk44Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk44Day5Status === -1">
                  <span v-if="scope.row.wk44Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk44Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk44Day5Status !== -1">
                  <span v-if="scope.row.wk44Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk44Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk44Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk44Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk44" header-align="center" :label="a446" prop="wk44Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk44Day6Status === -1">
                  <span v-if="scope.row.wk44Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk44Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk44Day6Status !== -1">
                  <span v-if="scope.row.wk44Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk44Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk44Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk44Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk44" header-align="center" :label="a447" prop="wk44Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk44Day7Status === -1">
                  <span v-if="scope.row.wk44Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk44Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk44Day7Status !== -1">
                  <span v-if="scope.row.wk44Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk44Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk44Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk44Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK45-Country" prop="wk45Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk45Status === -1">
                  <span v-if="scope.row.wk45Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk45Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk45Msrp }}</span>
               <b v-if="b451">+</b>
                      <b v-if="b452">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk45Status !== -1">
                  <span v-if="scope.row.wk45Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk45Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk45Msrp }}</span>
               <b v-if="b451">+</b>
                      <b v-if="b452">-</b>
                  </span>
                   <!-- <span v-else-if="scope.row.wk45Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk45Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK45-USD" prop="wk45MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk45Status === -1">
            <span v-if="scope.row.wk45MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk45Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk45MsrpUSD }}</span>
               <b v-if="b451">+</b>
                      <b v-if="b452">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk45Status !== -1">
                <span v-if="scope.row.wk45MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk45Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk45MsrpUSD }}</span>
                   <b v-if="b451">+</b>
                      <b v-if="b452">-</b>
               </span>
                <span v-else-if="scope.row.wk45Msrp !== null">
                          <span @click="showWk45Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk45Msrp }}</span>
                           <b v-if="b451">+</b>
                      <b v-if="b452">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk45" header-align="center" :label="a451" prop="wk45Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk45Day1Status === -1">
                  <span v-if="scope.row.wk45Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk45Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk45Day1Status !== -1">
                  <span v-if="scope.row.wk45Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk45Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk45Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk45Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk45" header-align="center" :label="a452" prop="wk45Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk45Day2Status === -1">
                  <span v-if="scope.row.wk45Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk45Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk45Day2Status !== -1">
                  <span v-if="scope.row.wk45Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk45Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk45Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk45Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk45" header-align="center" :label="a453" prop="wk45Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk45Day3Status === -1">
                  <span v-if="scope.row.wk45Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk45Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk45Day3Status !== -1">
                  <span v-if="scope.row.wk45Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk45Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk45Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk45Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk45" header-align="center" :label="a454" prop="wk45Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk45Day4Status === -1">
                  <span v-if="scope.row.wk45Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk45Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk45Day4Status !== -1">
                  <span v-if="scope.row.wk45Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk45Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk45Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk45Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk45" header-align="center" :label="a455" prop="wk45Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk45Day5Status === -1">
                  <span v-if="scope.row.wk45Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk45Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk45Day5Status !== -1">
                  <span v-if="scope.row.wk45Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk45Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk45Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk45Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk45" header-align="center" :label="a456" prop="wk45Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk45Day6Status === -1">
                  <span v-if="scope.row.wk45Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk45Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk45Day6Status !== -1">
                  <span v-if="scope.row.wk45Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk45Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk45Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk45Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk45" header-align="center" :label="a457" prop="wk45Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk45Day7Status === -1">
                  <span v-if="scope.row.wk45Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk45Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk45Day7Status !== -1">
                  <span v-if="scope.row.wk45Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk45Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk45Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk45Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK46-Country" prop="wk46Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk46Status === -1">
                  <span v-if="scope.row.wk46Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk46Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk46Msrp }}</span>
               <b v-if="b461">+</b>
                      <b v-if="b462">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk46Status !== -1">
                  <span v-if="scope.row.wk46Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk46Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk46Msrp }}</span>
              <b v-if="b461">+</b>
                      <b v-if="b462">-</b>
                  </span>
                   <!-- <span v-else-if="scope.row.wk46Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk46Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK46-USD" prop="wk46MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk46Status === -1">
            <span v-if="scope.row.wk46MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk46Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk46MsrpUSD }}</span>
              <b v-if="b461">+</b>
                      <b v-if="b462">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk46Status !== -1">
                <span v-if="scope.row.wk46MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk46Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk46MsrpUSD }}</span>
                  <b v-if="b461">+</b>
                      <b v-if="b462">-</b>
               </span>
                <span v-else-if="scope.row.wk46Msrp !== null">
                          <span @click="showWk46Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk46Msrp }}</span>
                          <b v-if="b461">+</b>
                      <b v-if="b462">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk46" header-align="center" :label="a461" prop="wk46Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk46Day1Status === -1">
                  <span v-if="scope.row.wk46Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk46Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk46Day1Status !== -1">
                  <span v-if="scope.row.wk46Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk46Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk46Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk46Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk46" header-align="center" :label="a462" prop="wk46Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk46Day2Status === -1">
                  <span v-if="scope.row.wk46Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk46Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk46Day2Status !== -1">
                  <span v-if="scope.row.wk46Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk46Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk46Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk46Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk46" header-align="center" :label="a463" prop="wk46Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk46Day3Status === -1">
                  <span v-if="scope.row.wk46Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk46Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk46Day3Status !== -1">
                  <span v-if="scope.row.wk46Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk46Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk46Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk46Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk46" header-align="center" :label="a464" prop="wk46Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk46Day4Status === -1">
                  <span v-if="scope.row.wk46Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk46Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk46Day4Status !== -1">
                  <span v-if="scope.row.wk46Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk46Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk46Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk46Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk46" header-align="center" :label="a465" prop="wk46Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk46Day5Status === -1">
                  <span v-if="scope.row.wk46Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk46Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk46Day5Status !== -1">
                  <span v-if="scope.row.wk46Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk46Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk1Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk46Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk46" header-align="center" :label="a466" prop="wk46Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk46Day6Status === -1">
                  <span v-if="scope.row.wk46Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk46Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk46Day6Status !== -1">
                  <span v-if="scope.row.wk46Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk46Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk46Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk46Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk46" header-align="center" :label="a467" prop="wk46Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk46Day7Status === -1">
                  <span v-if="scope.row.wk46Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk46Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk46Day7Status !== -1">
                  <span v-if="scope.row.wk46Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk46Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk46Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk46Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK47-Country" prop="wk47Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk47Status === -1">
                  <span v-if="scope.row.wk47Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk47Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk47Msrp }}</span>
              <b v-if="b471">+</b>
                      <b v-if="b472">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk47Status !== -1">
                  <span v-if="scope.row.wk47Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk47Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk47Msrp }}</span>
                <b v-if="b471">+</b>
                      <b v-if="b472">-</b>
                  </span>
                   <!-- <span v-else-if="scope.row.wk47Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk47Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK47-USD" prop="wk47MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk47Status === -1">
            <span v-if="scope.row.wk47MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk47Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk47MsrpUSD }}</span>
                <b v-if="b471">+</b>
                      <b v-if="b472">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk47Status !== -1">
                <span v-if="scope.row.wk47MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk47Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk47MsrpUSD }}</span>
                    <b v-if="b471">+</b>
                      <b v-if="b472">-</b>
               </span>
                <span v-else-if="scope.row.wk47Msrp !== null">
                          <span @click="showWk47Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk47Msrp }}</span>
                            <b v-if="b471">+</b>
                      <b v-if="b472">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk47" header-align="center" :label="a471" prop="wk47Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk47Day1Status === -1">
                  <span v-if="scope.row.wk47Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk47Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk47Day1Status !== -1">
                  <span v-if="scope.row.wk47Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk47Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk47Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk47Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk47" header-align="center" :label="a472" prop="wk47Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk47Day2Status === -1">
                  <span v-if="scope.row.wk47Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk47Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk47Day2Status !== -1">
                  <span v-if="scope.row.wk47Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk47Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk47Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk47Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk47" header-align="center" :label="a473" prop="wk47Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk47Day3Status === -1">
                  <span v-if="scope.row.wk47Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk47Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk47Day3Status !== -1">
                  <span v-if="scope.row.wk47Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk47Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk47Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk47Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk47" header-align="center" :label="a474" prop="wk47Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk47Day4Status === -1">
                  <span v-if="scope.row.wk47Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk47Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk47Day4Status !== -1">
                  <span v-if="scope.row.wk47Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk47Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk47Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk47Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk47" header-align="center" :label="a475" prop="wk47Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk47Day5Status === -1">
                  <span v-if="scope.row.wk47Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk47Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk47Day5Status !== -1">
                  <span v-if="scope.row.wk47Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk47Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk47Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk47Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk47" header-align="center" :label="a476" prop="wk47Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk47Day6Status === -1">
                  <span v-if="scope.row.wk47Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk47Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk47Day6Status !== -1">
                  <span v-if="scope.row.wk47Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk47Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk47Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk47Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk47" header-align="center" :label="a477" prop="wk47Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk47Day7Status === -1">
                  <span v-if="scope.row.wk47Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk47Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk47Day7Status !== -1">
                  <span v-if="scope.row.wk47Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk47Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk47Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk47Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column  :key="Math.random()" v-if="showCountry" header-align="center" label="WK48-Country" prop="wk48Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk48Status === -1">
                  <span v-if="scope.row.wk48Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk48Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk48Msrp }}</span>
                <b v-if="b481">+</b>
                      <b v-if="b482">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk48Status !== -1">
                  <span v-if="scope.row.wk48Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk48Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk48Msrp }}</span>
                <b v-if="b481">+</b>
                      <b v-if="b482">-</b>
                  </span>
                   <!-- <span v-else-if="scope.row.wk48Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk48Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK48-USD" prop="wk48MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk48Status === -1">
            <span v-if="scope.row.wk48MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk48Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk48MsrpUSD }}</span>
                <b v-if="b481">+</b>
                      <b v-if="b482">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk48Status !== -1">
                <span v-if="scope.row.wk48MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk48Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk48MsrpUSD }}</span>
                    <b v-if="b481">+</b>
                      <b v-if="b482">-</b>
               </span>
                <span v-else-if="scope.row.wk48Msrp !== null">
                          <span @click="showWk48Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk48Msrp }}</span>
                            <b v-if="b481">+</b>
                      <b v-if="b482">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk48" header-align="center" :label="a481" prop="wk48Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk48Day1Status === -1">
                  <span v-if="scope.row.wk48Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk48Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk48Day1Status !== -1">
                  <span v-if="scope.row.wk48Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk48Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk48Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk48Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk48" header-align="center" :label="a482" prop="wk48Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk48Day2Status === -1">
                  <span v-if="scope.row.wk48Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk48Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk48Day2Status !== -1">
                  <span v-if="scope.row.wk48Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk48Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk48Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk48Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk48" header-align="center" :label="a483" prop="wk48Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk48Day3Status === -1">
                  <span v-if="scope.row.wk48Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk48Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk48Day3Status !== -1">
                  <span v-if="scope.row.wk48Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk48Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk48Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk48Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk48" header-align="center" :label="a484" prop="wk48Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk48Day4Status === -1">
                  <span v-if="scope.row.wk48Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk48Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk48Day4Status !== -1">
                  <span v-if="scope.row.wk48Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk48Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk48Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk48Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk48" header-align="center" :label="a485" prop="wk48Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk48Day5Status === -1">
                  <span v-if="scope.row.wk48Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk48Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk48Day5Status !== -1">
                  <span v-if="scope.row.wk48Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk48Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk48Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk48Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk48" header-align="center" :label="a486" prop="wk48Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk48Day6Status === -1">
                  <span v-if="scope.row.wk48Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk48Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk48Day6Status !== -1">
                  <span v-if="scope.row.wk48Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk48Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk48Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk48Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk48" header-align="center" :label="a487" prop="wk48Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk48Day7Status === -1">
                  <span v-if="scope.row.wk48Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk48Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk48Day7Status !== -1">
                  <span v-if="scope.row.wk48Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk48Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk48Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk48Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK49-Country" prop="wk49Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk49Status === -1">
                  <span v-if="scope.row.wk49Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk49Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk49Msrp }}</span>
                <b v-if="b491">+</b>
                      <b v-if="b492">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk49Status !== -1">
                  <span v-if="scope.row.wk49Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk49Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk49Msrp }}</span>
                <b v-if="b491">+</b>
                      <b v-if="b492">-</b>
                  </span>
                   <!-- <span v-else-if="scope.row.wk49Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk49Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK49-USD" prop="wk49MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk49Status === -1">
            <span v-if="scope.row.wk49MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk49Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk49MsrpUSD }}</span>
                <b v-if="b491">+</b>
                      <b v-if="b492">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk49Status !== -1">
                <span v-if="scope.row.wk49MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk49Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk49MsrpUSD }}</span>
                    <b v-if="b491">+</b>
                      <b v-if="b492">-</b>
               </span>
                <span v-else-if="scope.row.wk49Msrp !== null">
                          <span @click="showWk49Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk49Msrp }}</span>
                            <b v-if="b491">+</b>
                      <b v-if="b492">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk49" header-align="center" :label="a491" prop="wk49Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk49Day1Status === -1">
                  <span v-if="scope.row.wk49Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk49Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk49Day1Status !== -1">
                  <span v-if="scope.row.wk49Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk49Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk49Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk49Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk49" header-align="center" :label="a492" prop="wk49Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk49Day2Status === -1">
                  <span v-if="scope.row.wk49Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk49Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk49Day2Status !== -1">
                  <span v-if="scope.row.wk49Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk49Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk49Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk49Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk49" header-align="center" :label="a493" prop="wk49Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk49Day3Status === -1">
                  <span v-if="scope.row.wk49Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk49Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk49Day3Status !== -1">
                  <span v-if="scope.row.wk49Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk49Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk49Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk49Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk49" header-align="center" :label="a494" prop="wk49Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk49Day4Status === -1">
                  <span v-if="scope.row.wk49Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk49Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk49Day4Status !== -1">
                  <span v-if="scope.row.wk49Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk49Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk49Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk49Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk49" header-align="center" :label="a495" prop="wk49Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk49Day5Status === -1">
                  <span v-if="scope.row.wk49Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk49Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk49Day5Status !== -1">
                  <span v-if="scope.row.wk49Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk49Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk49Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk49Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk49" header-align="center" :label="a496" prop="wk49Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk49Day6Status === -1">
                  <span v-if="scope.row.wk49Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk49Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk49Day6Status !== -1">
                  <span v-if="scope.row.wk49Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk49Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk49Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk49Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk49" header-align="center" :label="a497" prop="wk49Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk49Day7Status === -1">
                  <span v-if="scope.row.wk49Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk49Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk49Day7Status !== -1">
                  <span v-if="scope.row.wk49Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk49Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk49Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk49Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK50-Country" prop="wk50Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk50Status === -1">
                  <span v-if="scope.row.wk50Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk50Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk50Msrp }}</span>
                <b v-if="b501">+</b>
                      <b v-if="b502">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk50Status !== -1">
                  <span v-if="scope.row.wk50Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk50Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk50Msrp }}</span>
                  <b v-if="b501">+</b>
                      <b v-if="b502">-</b>
                  </span>
                   <!-- <span v-else-if="scope.row.wk50Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk50Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK50-USD" prop="wk50MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk50Status === -1">
            <span v-if="scope.row.wk50MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk50Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk50MsrpUSD }}</span>
                  <b v-if="b501">+</b>
                      <b v-if="b502">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk50Status !== -1">
                <span v-if="scope.row.wk50MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk50Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk50MsrpUSD }}</span>
                      <b v-if="b501">+</b>
                      <b v-if="b502">-</b>
               </span>
                <span v-else-if="scope.row.wk50Msrp !== null">
                          <span @click="showWk50Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk50Msrp }}</span>
                              <b v-if="b501">+</b>
                      <b v-if="b502">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk50" header-align="center" :label="a501" prop="wk50Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk50Day1Status === -1">
                  <span v-if="scope.row.wk50Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk50Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk50Day1Status !== -1">
                  <span v-if="scope.row.wk50Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk50Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk50Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk50Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk50" header-align="center" :label="a502" prop="wk50Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk50Day2Status === -1">
                  <span v-if="scope.row.wk50Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk50Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk50Day2Status !== -1">
                  <span v-if="scope.row.wk50Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk50Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk50Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk50Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk50" header-align="center" :label="a503" prop="wk50Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk50Day3Status === -1">
                  <span v-if="scope.row.wk50Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk50Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk50Day3Status !== -1">
                  <span v-if="scope.row.wk50Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk50Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk50Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk50Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk50" header-align="center" :label="a504" prop="wk50Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk50Day4Status === -1">
                  <span v-if="scope.row.wk50Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk50Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk50Day4Status !== -1">
                  <span v-if="scope.row.wk50Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk50Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk50Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk50Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk50" header-align="center" :label="a505" prop="wk50Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk50Day5Status === -1">
                  <span v-if="scope.row.wk50Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk50Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk50Day5Status !== -1">
                  <span v-if="scope.row.wk50Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk50Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk50Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk50Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk50" header-align="center" :label="a506" prop="wk50Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk50Day6Status === -1">
                  <span v-if="scope.row.wk50Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk50Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk50Day6Status !== -1">
                  <span v-if="scope.row.wk50Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk50Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk50Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk50Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk50" header-align="center" :label="a507" prop="wk50Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk50Day7Status === -1">
                  <span v-if="scope.row.wk50Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk50Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk50Day7Status !== -1">
                  <span v-if="scope.row.wk50Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk50Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk50Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk50Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK51-Country" prop="wk51Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk51Status === -1">
                  <span v-if="scope.row.wk51Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk51Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk51Msrp }}</span>
                  <b v-if="b511">+</b>
                      <b v-if="b512">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk51Status !== -1">
                  <span v-if="scope.row.wk51Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk51Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk51Msrp }}</span>
                 <b v-if="b511">+</b>
                      <b v-if="b512">-</b>
                  </span>
                   <!-- <span v-else-if="scope.row.wk51Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk51Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK51-USD" prop="wk51MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk51Status === -1">
            <span v-if="scope.row.wk51MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk51Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk51MsrpUSD }}</span>
                 <b v-if="b511">+</b>
                      <b v-if="b512">-</b>
            </span>
          </span>   
           <span v-else-if="scope.row.wk51Status !== -1">
                <span v-if="scope.row.wk51MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk51Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk51MsrpUSD }}</span>
                     <b v-if="b511">+</b>
                      <b v-if="b512">-</b>
               </span>
                <span v-else-if="scope.row.wk51Msrp !== null">
                          <span @click="showWk51Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk51Msrp }}</span>
                             <b v-if="b511">+</b>
                      <b v-if="b512">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk51" header-align="center" :label="a511" prop="wk51Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk51Day1Status === -1">
                  <span v-if="scope.row.wk51Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk51Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk51Day1Status !== -1">
                  <span v-if="scope.row.wk51Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk51Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk51Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk51Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk51" header-align="center" :label="a512" prop="wk51Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk51Day2Status === -1">
                  <span v-if="scope.row.wk51Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk51Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk51Day2Status !== -1">
                  <span v-if="scope.row.wk51Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk51Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk51Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk51Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk51" header-align="center" :label="a513" prop="wk51Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk51Day3Status === -1">
                  <span v-if="scope.row.wk51Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk51Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk51Day3Status !== -1">
                  <span v-if="scope.row.wk51Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk51Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk51Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk51Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk51" header-align="center" :label="a514" prop="wk51Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk51Day4Status === -1">
                  <span v-if="scope.row.wk51Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk51Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk51Day4Status !== -1">
                  <span v-if="scope.row.wk51Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk51Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk51Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk51Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk51" header-align="center" :label="a515" prop="wk51Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk51Day5Status === -1">
                  <span v-if="scope.row.wk51Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk51Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk51Day5Status !== -1">
                  <span v-if="scope.row.wk51Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk51Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk51Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk51Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk51" header-align="center" :label="a516" prop="wk51Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk51Day6Status === -1">
                  <span v-if="scope.row.wk51Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk51Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk51Day6Status !== -1">
                  <span v-if="scope.row.wk51Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk51Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk51Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk51Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk51" header-align="center" :label="a517" prop="wk51Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk51Day7Status === -1">
                  <span v-if="scope.row.wk51Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk51Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk51Day7Status !== -1">
                  <span v-if="scope.row.wk51Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk51Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk51Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk51Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK52-Country" prop="wk52Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk52Status === -1">
                  <span v-if="scope.row.wk52Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk52Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk52Msrp }}</span>
                 <b v-if="b521">+</b>
                      <b v-if="b522">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk52Status !== -1">
                  <span v-if="scope.row.wk52Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk52Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk52Msrp }}</span>
               <b v-if="b521">+</b>
                      <b v-if="b522">-</b>
                  </span>
                   <!-- <span v-else-if="scope.row.wk52Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk52Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK52-USD" prop="wk52MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk52Status === -1">
            <span v-if="scope.row.wk52MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk52Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk52MsrpUSD }}</span>
               <b v-if="b521">+</b>
                      <b v-if="b522">-</b>
            </span> 
          </span>   
           <span v-else-if="scope.row.wk52Status !== -1">
                <span v-if="scope.row.wk52MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk52Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk52MsrpUSD }}</span>
                   <b v-if="b521">+</b>
                      <b v-if="b522">-</b>
               </span>
                <span v-else-if="scope.row.wk52Msrp !== null">
                          <span @click="showWk52Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk52Msrp }}</span>
                           <b v-if="b521">+</b>
                      <b v-if="b522">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk52" header-align="center" :label="a521" prop="wk52Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk52Day1Status === -1">
                  <span v-if="scope.row.wk52Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk52Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk52Day1Status !== -1">
                  <span v-if="scope.row.wk52Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk52Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk52Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk52Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk52" header-align="center" :label="a522" prop="wk52Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk52Day2Status === -1">
                  <span v-if="scope.row.wk52Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk52Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk52Day2Status !== -1">
                  <span v-if="scope.row.wk52Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk52Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk52Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk52Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk52" header-align="center" :label="a523" prop="wk52Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk52Day3Status === -1">
                  <span v-if="scope.row.wk52Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk52Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk52Day3Status !== -1">
                  <span v-if="scope.row.wk52Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk52Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk52Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk52Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk52" header-align="center" :label="a524" prop="wk52Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk52Day4Status === -1">
                  <span v-if="scope.row.wk52Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk52Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk52Day4Status !== -1">
                  <span v-if="scope.row.wk52Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk52Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk52Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk52Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk52" header-align="center" :label="a525" prop="wk52Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk52Day5Status === -1">
                  <span v-if="scope.row.wk52Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk52Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk52Day5Status !== -1">
                  <span v-if="scope.row.wk52Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk52Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk52Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk52Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk52" header-align="center" :label="a526" prop="wk52Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk52Day6Status === -1">
                  <span v-if="scope.row.wk52Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk52Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk52Day6Status !== -1">
                  <span v-if="scope.row.wk52Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk52Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk52Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk52Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk52" header-align="center" :label="a527" prop="wk52Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk52Day7Status === -1">
                  <span v-if="scope.row.wk52Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk52Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk52Day7Status !== -1">
                  <span v-if="scope.row.wk52Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk52Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk52Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk52Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
      <!-- <el-table-column header-align="center" label="Ceate Time" prop="createTime" align="center" width="180">
      </el-table-column> -->

        <el-table-column :key="Math.random()" v-if="showCountry" header-align="center" label="WK53-Country" prop="wk53Msrp" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk53Status === -1">
                  <span v-if="scope.row.wk53Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk53Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk53Msrp }}</span>
                 <b v-if="b531">+</b>
                      <b v-if="b532">-</b>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk53Status !== -1">
                  <span v-if="scope.row.wk53Msrp !== null">
                <!-- <b>$</b> -->
              <span @click="showWk53Method(scope.row)" style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk53Msrp }}</span>
               <b v-if="b531">+</b>
                      <b v-if="b532">-</b>
                  </span>
                   <!-- <span v-else-if="scope.row.wk52Msrp !== null">
                          <span style="margin-left: 10px;">{{ scope.row.currencyTypeWK }} {{ scope.row.wk52Msrp }}</span>
                        </span> -->
              </span>   
          </template>
      </el-table-column>
      <el-table-column :key="Math.random()" v-if="showUSD" header-align="center" label="WK53-USD" prop="wk53MsrpUSD" align="center" :show-overflow-tooltip="true" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.wk53Status === -1">
            <span v-if="scope.row.wk53MsrpUSD !== null">
              <!-- <b>$</b> -->
              <span @click="showWk53Method(scope.row)" style="margin-left: 10px;color: red;">{{ scope.row.wk53MsrpUSD }}</span>
               <b v-if="b531">+</b>
                      <b v-if="b532">-</b>
            </span> 
          </span>   
           <span v-else-if="scope.row.wk53Status !== -1">
                <span v-if="scope.row.wk53MsrpUSD !== null">
                  <!-- <b>$</b> -->
                  <span @click="showWk53Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk53MsrpUSD }}</span>
                   <b v-if="b531">+</b>
                      <b v-if="b532">-</b>
               </span>
                <span v-else-if="scope.row.wk53Msrp !== null">
                          <span @click="showWk53Method(scope.row)" style="margin-left: 10px;">{{ scope.row.wk53Msrp }}</span>
                           <b v-if="b531">+</b>
                      <b v-if="b532">-</b>
                        </span>
          </span>   
      </template>
      </el-table-column> 
          <el-table-column :key="Math.random()" v-if="showWk53" header-align="center" :label="a531" prop="wk53Day1" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk53Day1Status === -1">
                  <span v-if="scope.row.wk53Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk53Day1 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk53Day1Status !== -1">
                  <span v-if="scope.row.wk53Day1 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk53Day1 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk53Day1 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk53Day1 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk53" header-align="center" :label="a532" prop="wk53Day2" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk53Day2Status === -1">
                  <span v-if="scope.row.wk53Day2 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk53Day2 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk53Day2Status !== -1">
                  <span v-if="scope.row.wk53Day2 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk53Day2 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk53Day2 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk53Day2 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk53" header-align="center" :label="a533" prop="wk53Day3" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk53Day3Status === -1">
                  <span v-if="scope.row.wk53Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk53Day3 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk53Day3Status !== -1">
                  <span v-if="scope.row.wk53Day3 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk53Day3 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk53Day3 !== null">
                          <span  style="margin-left: 10px;">{{ scope.row.wk53Day3 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk53" header-align="center" :label="a534" prop="wk53Day4" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk53Day4Status === -1">
                  <span v-if="scope.row.wk53Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk53Day4 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk53Day4Status !== -1">
                  <span v-if="scope.row.wk53Day4 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;">{{ scope.row.wk53Day4 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk53Day4 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk53Day4 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk53" header-align="center" :label="a535" prop="wk53Day5" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk53Day5Status === -1">
                  <span v-if="scope.row.wk53Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk53Day5 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk53Day5Status !== -1">
                  <span v-if="scope.row.wk53Day5 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk53Day5 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk53Day5 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk53Day5 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk53" header-align="center" :label="a536" prop="wk53Day6" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk53Day6Status === -1">
                  <span v-if="scope.row.wk53Day6 !== null">
                <!-- <b>$</b> -->
              <span  style="margin-left: 10px;color: red;">{{ scope.row.wk53Day6 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk53Day6Status !== -1">
                  <span v-if="scope.row.wk53Day6 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk53Day6 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk53Day6 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk53Day6 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>

          <el-table-column :key="Math.random()" v-if="showWk53" header-align="center" :label="a537" prop="wk53Day7" align="center" :show-overflow-tooltip="true" width="120">
               <template slot-scope="scope">
              <span v-if="scope.row.wk53Day7Status === -1">
                  <span v-if="scope.row.wk53Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;color: red;">{{ scope.row.wk53Day7 }}</span>
                  </span>
              </span>   
              <span v-else-if="scope.row.wk53Day7Status !== -1">
                  <span v-if="scope.row.wk53Day7 !== null">
                <!-- <b>$</b> -->
              <span style="margin-left: 10px;">{{ scope.row.wk53Day7 }}</span>
                  </span>
                   <span v-else-if="scope.row.wk53Day7 !== null">
                          <span style="margin-left: 10px;">{{ scope.row.wk53Day7 }}</span>
                        </span>
              </span>   
          </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getCalendarList"
    />
  </div>
</template>

<script>
import { listCalendar,updateMsrpOtherMethod,getCountryList } from "@/api/tek/calendar/calendar";
export default {
  name: "Role",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 角色表格数据
      roleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sku: undefined,
        customerName: undefined,
        modelName: undefined,
        year: undefined,
        country: undefined,
        skuEncode: undefined
      },
      options: [],
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
        roleName: [
          { required: true, message: "角色名称不能为空", trigger: "blur" }
        ],
        roleKey: [
          { required: true, message: "权限字符不能为空", trigger: "blur" }
        ],
        roleSort: [
          { required: true, message: "角色顺序不能为空", trigger: "blur" }
        ]
      },
      newTekCalendarVOList: [],
      customerList: [],
      skuList: [],
      skuListNew: [],
      customerListNew: [],
      msrpScopelist: [
        {
          key: "35",
          name: "35"
        },
        {
          key: "40",
          name: "40"
        },
        {
          key: "45",
          name: "45"
        },
        {
          key: "50",
          name: "50"
        },
        {
          key: "55",
          name: "55"
        },
          {
          key: "60",
          name: "60"
        },
          {
          key: "65",
          name: "65"
        },
          {
          key: "70",
          name: "70"
        },
          {
          key: "75",
          name: "75"
        },
          {
          key: "80",
          name: "80"
        },
          {
          key: "85",
          name: "85"
        },
          {
          key: "90",
          name: "90"
        },
          {
          key: "95",
          name: "95"
        },
           {
          key: "100",
          name: "100"
        }
      ],
      newTekMsrp: {
        id: '',
        msrpOther: ''
      },
      customerName: '',
      showCountry: true,
      showUSD: true,
      showWk1: false,
      showWk2: false,
      showWk3: false,
      showWk4: false,
      showWk5: false,
      showWk6: false,
      showWk7: false,
      showWk8: false,
      showWk9: false,
      showWk10: false,
      showWk11: false,
      showWk12: false,
      showWk13: false,
      showWk14: false,
      showWk15: false,
      showWk16: false,
      showWk17: false,
      showWk18: false,
      showWk19: false,
      showWk20: false,
      showWk21: false,
      showWk22: false,
      showWk23: false,
      showWk24: false,
      showWk25: false,
      showWk26: false,
      showWk27: false,
      showWk28: false,
      showWk29: false,
      showWk30: false,
      showWk31: false,
      showWk32: false,
      showWk33: false,
      showWk34: false,
      showWk35: false,
      showWk36: false,
      showWk37: false,
      showWk38: false,
      showWk39: false,
      showWk40: false,
      showWk41: false,
      showWk42: false,
      showWk43: false, 
      showWk44: false,
      showWk45: false,
      showWk46: false,
      showWk47: false,
      showWk48: false,
      showWk49: false,
      showWk50: false,
      showWk51: false,
      showWk52: false,
      showWk53: false,
      a011:'',
      a012:'',
      a013:'',
      a014:'',
      a015:'',
      a016:'',
      a017:'',
      a021:'',
      a022:'',
      a023:'',
      a024:'',
      a025:'',
      a026:'',
      a027:'',
      a031:'',
      a032:'',
      a033:'',
      a034:'',
      a035:'',
      a036:'',
      a037:'',
      a041:'',
      a042:'',
      a043:'',
      a044:'',
      a045:'',
      a046:'',
      a047:'',
      a051:'',
      a052:'',
      a053:'',
      a054:'',
      a055:'',
      a056:'',
      a057:'',
      a061:'',
      a062:'',
      a063:'',
      a064:'',
      a065:'',
      a066:'',
      a067:'',
      a071:'',
      a072:'',
      a073:'',
      a074:'',
      a075:'',
      a076:'',
      a077:'',
      a081:'',
      a082:'',
      a083:'',
      a084:'',
      a085:'',
      a086:'',
      a087:'',
      a091:'',
      a092:'',
      a093:'',
      a094:'',
      a095:'',
      a096:'',
      a097:'',
      a101:'',
      a102:'',
      a103:'',
      a104:'',
      a105:'',
      a106:'',
      a107:'',
      a111:'',
      a112:'',
      a113:'',
      a114:'',
      a115:'',
      a116:'',
      a117:'',
      a121:'',
      a122:'',
      a123:'',
      a124:'',
      a125:'',
      a126:'',
      a127:'',
      a131:'',
      a132:'',
      a133:'',
      a134:'',
      a135:'',
      a136:'',
      a137:'',
      a141:'',
      a142:'',
      a143:'',
      a144:'',
      a145:'',
      a146:'',
      a147:'',
      a151:'',
      a152:'',
      a153:'',
      a154:'',
      a155:'',
      a156:'',
      a157:'',
      a161:'',
      a162:'',
      a163:'',
      a164:'',
      a165:'',
      a166:'',
      a167:'',
      a171:'',
      a172:'',
      a173:'',
      a174:'',
      a175:'',
      a176:'',
      a177:'',
      a181:'',
      a182:'',
      a183:'',
      a184:'',
      a185:'',
      a186:'',
      a187:'',
      a191:'',
      a192:'',
      a193:'',
      a194:'',
      a195:'',
      a196:'',
      a197:'',
      a201:'',
      a202:'',
      a203:'',
      a204:'',
      a205:'',
      a206:'',
      a207:'',
      a211:'',
      a212:'',
      a213:'',
      a214:'',
      a215:'',
      a216:'',
      a217:'',
      a221:'',
      a222:'',
      a223:'',
      a224:'',
      a225:'',
      a226:'',
      a227:'',
      a231:'',
      a232:'',
      a233:'',
      a234:'',
      a235:'',
      a236:'',
      a237:'',
      a241:'',
      a242:'',
      a243:'',
      a244:'',
      a245:'',
      a246:'',
      a247:'',
      a251:'',
      a252:'',
      a253:'',
      a254:'',
      a255:'',
      a256:'',
      a257:'',
      a261:'',
      a262:'',
      a263:'',
      a264:'',
      a265:'',
      a266:'',
      a267:'',
      a271:'',
      a272:'',
      a273:'',
      a274:'',
      a275:'',
      a276:'',
      a277:'',
      a281:'',
      a282:'',
      a283:'',
      a284:'',
      a285:'',
      a286:'',
      a287:'',
      a291:'',
      a292:'',
      a293:'',
      a294:'',
      a295:'',
      a296:'',
      a297:'',
      a301:'',
      a302:'',
      a303:'',
      a304:'',
      a305:'',
      a306:'',
      a307:'',
      a311:'',
      a312:'',
      a313:'',
      a314:'',
      a315:'',
      a316:'',
      a317:'',
      a321:'',
      a322:'',
      a323:'',
      a324:'',
      a325:'',
      a326:'',
      a327:'',
      a331:'',
      a332:'',
      a333:'',
      a334:'',
      a335:'',
      a336:'',
      a337:'',
      a341:'',
      a342:'',
      a343:'',
      a344:'',
      a345:'',
      a346:'',
      a347:'',
      a351:'',
      a352:'',
      a353:'',
      a354:'',
      a355:'',
      a356:'',
      a357:'',
      a361:'',
      a362:'',
      a363:'',
      a364:'',
      a365:'',
      a366:'',
      a367:'',
      a371:'',
      a372:'',
      a373:'',
      a374:'',
      a375:'',
      a376:'',
      a377:'',
      a381:'',
      a382:'',
      a383:'',
      a384:'',
      a385:'',
      a386:'',
      a387:'',
      a391:'',
      a392:'',
      a393:'',
      a394:'',
      a395:'',
      a396:'',
      a397:'',
      a401:'',
      a402:'',
      a403:'',
      a404:'',
      a405:'',
      a406:'',
      a407:'',
      a411:'',
      a412:'',
      a413:'',
      a414:'',
      a415:'',
      a416:'',
      a417:'',
      a421:'',
      a422:'',
      a423:'',
      a424:'',
      a425:'',
      a426:'',
      a427:'',
      a431:'',
      a432:'',
      a433:'',
      a434:'',
      a435:'',
      a436:'',
      a437:'',
      a441:'',
      a442:'',
      a443:'',
      a444:'',
      a445:'',
      a446:'',
      a447:'',
      a451:'',
      a452:'',
      a453:'',
      a454:'',
      a455:'',
      a456:'',
      a457:'',
      a461:'',
      a462:'',
      a463:'',
      a464:'',
      a465:'',
      a466:'',
      a467:'',
      a471:'',
      a472:'',
      a473:'',
      a474:'',
      a475:'',
      a476:'',
      a477:'',
      a481:'',
      a482:'',
      a483:'',
      a484:'',
      a485:'',
      a486:'',
      a487:'',
      a491:'',
      a492:'',
      a493:'',
      a494:'',
      a495:'',
      a496:'',
      a497:'',
      a501:'',
      a502:'',
      a503:'',
      a504:'',
      a505:'',
      a506:'',
      a507:'',
      a511:'',
      a512:'',
      a513:'',
      a514:'',
      a515:'',
      a516:'',
      a517:'',
      a521:'',
      a522:'',
      a523:'',
      a524:'',
      a525:'',
      a526:'',
      a527:'',
      a531:'',
      a532:'',
      a533:'',
      a534:'',
      a535:'',
      a536:'',
      a537:'',
      b011: true,
      b012: false,
      b021: true,
      b022: false,
      b031: true,
      b032: false,
      b041: true,
      b042: false,
      b051: true,
      b052: false,
      b061: true,
      b062: false,
      b071: true,
      b072: false,
      b081: true,
      b082: false,
      b091: true,
      b092: false,
      b101: true,
      b102: false,
      b111: true,
      b112: false,
      b121: true,
      b122: false,
      b131: true,
      b132: false,
      b141: true,
      b142: false,
      b151: true,
      b152: false,
      b161: true,
      b162: false,
      b171: true,
      b172: false,
      b181: true,
      b182: false,
      b191: true,
      b192: false,
      b201: true,
      b202: false,
      b211: true,
      b212: false,
      b221: true,
      b222: false,
      b231: true,
      b232: false,
      b241: true,
      b242: false,
      b251: true,
      b252: false,
      b261: true,
      b262: false,
      b271: true,
      b272: false,
      b281: true,
      b282: false,
      b291: true,
      b292: false,
      b301: true,
      b302: false,
      b311: true,
      b312: false,
      b321: true,
      b322: false,
      b331: true,
      b332: false,
      b341: true,
      b342: false,
      b351: true,
      b352: false,
      b361: true,
      b362: false,
      b371: true,
      b372: false,
      b381: true,
      b382: false,
      b391: true,
      b392: false,
      b401: true,
      b402: false,
      b411: true,
      b412: false,
      b421: true,
      b422: false,
      b431: true,
      b432: false,
      b441: true,
      b442: false,
      b451: true,
      b452: false,
      b461: true,
      b462: false,
      b471: true,
      b472: false,
      b481: true,
      b482: false,
      b491: true,
      b492: false,
      b501: true,
      b502: false,
      b511: true,
      b512: false,
      b521: true,
      b522: false,
      b531: true,
      b532: false
      
    };
  },
  created() {
    this.getCalendarList();
    this.getCountryListForSelect();
  },
  methods: {
    /** 查询建议零售价列表 */
      getCalendarList() {
      this.loading = true;
      listCalendar(this.addDateRange(this.queryParams, this.dateRange)).then(
        response => {
         if(response.rows[0] !== undefined){
              this.customerList = response.rows[0].customerList;
              if(this.customerList != null){
                this.customerListNew = [];
                  this.customerList.forEach(item =>{
                    this.customerListNew.push({
                      id: item,
                      value: item
                    })
                  })
              }
              this.skuList = response.rows[0].skuList;
              if(this.skuList != null){
                this.skuListNew = [];
                this.skuList.forEach(item =>{
                this.skuListNew.push({
                  id: item,
                  value: item
                })
              })
              }
         }
          this.newTekCalendarVOList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },

    /**列表中修改msrpOther值 */
      updateMsrpOther(data){
        this.newTekMsrp.id = data.msrpId;
        this.newTekMsrp.msrpOther = data.msrpOther;
        this.loading = true;
      updateMsrpOtherMethod(this.newTekMsrp).then(
        response => {
        this.getCalendarList();
          this.loading = false;
        }
      );
    },

      //row,  每一行上的数据
      //column, 每一列上的数据
      //rowIndex,  行数的下标从0开始
      //columnIndex   列数下标从0开始
        columnStyle({ row, column, rowIndex, columnIndex }) {
	          //  if ((columnIndex == 6 || columnIndex == 7 ) && rowIndex == 0) {   
	          // return 'background-color:DeepSkyBlue;'
            // } 
        },
                /* fetch-suggestions 是一个返回输入建议的方法属性，在该方法中你可以在输入建议数据准备好时通过 cb(data) 返回到 autocomplete 组件中. */
        querySearch (queryString, cb) {
          const customerListNew = this.customerListNew
          const results = queryString
            ? customerListNew.filter(this.createFilter(queryString))
            : customerListNew
          // 调用 callback 返回建议列表的数据
          cb(results)
        },
    createFilter (queryString) {
          return customerName => {
            return (
           customerName.value.toLowerCase().indexOf(queryString.toLowerCase()) !== -1  // 改为===0 就是筛选的数据只是首字匹配的列表项，需要包含输入字的所有列表项改为！==-1
            )
          }
        },
     /* 跳转并传值 */
        handleSelect (item) {
        },
        handleIconClick (ev) {
        },

               /* fetch-suggestions 是一个返回输入建议的方法属性，在该方法中你可以在输入建议数据准备好时通过 cb(data) 返回到 autocomplete 组件中. */
        querySearchSku (queryString, cb) {
          const skuListNew = this.skuListNew
          const results = queryString
            ? skuListNew.filter(this.createFilterSku(queryString))
            : skuListNew
          // 调用 callback 返回建议列表的数据
          cb(results)
        },
    createFilterSku (queryString) {
          return sku => {
            return (
           sku.value.toLowerCase().indexOf(queryString.toLowerCase()) !== -1  // 改为===0 就是筛选的数据只是首字匹配的列表项，需要包含输入字的所有列表项改为！==-1
            )
          }
        },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 取消按钮（数据权限）
    cancelDataScope() {
      this.openDataScope = false;
      this.reset();
    },
    // 表单重置
    reset() {
      if (this.$refs.menu != undefined) {
        this.$refs.menu.setCheckedKeys([]);
      }
      this.form = {
        roleId: undefined,
        roleName: undefined,
        roleKey: undefined,
        roleSort: 0,
        status: "0",
        menuIds: [],
        deptIds: [],
        remark: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getCalendarList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.queryParams.skuEncode = null;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.roleId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
  /** 展示wk-Country列 */
    showCountryMethod() {
        if(this.showUSD === true){
          this.showUSD = false;
        }else if(this.showUSD === false){
          this.showUSD = true;
        }
    },
      /** 展示wk-USD列 */
    showUSDMethod() {
        if(this.showCountry === true){
          this.showCountry = false;
        }else if(this.showCountry === false){
          this.showCountry = true;
        }
    },
      /**全部展示country+USD */
    showAllMethod() {
     if(this.showCountry === false && this.showUSD === false){
          this.showCountry = true;
          this.showUSD = true;
        }else if(this.showCountry === true && this.showUSD === false){
          this.showUSD = true;
        }else if(this.showCountry === false && this.showUSD === true){
          this.showCountry = true;
        }
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getMenuTreeselect();
      this.open = true;
      this.title = "添加角色";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const roleId = row.roleId || this.ids
      this.$nextTick(() => {
        this.getRoleMenuTreeselect(roleId);
      });
      getRole(roleId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改角色";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.roleId != undefined) {
            this.form.menuIds = this.getMenuAllCheckedKeys();
            updateRole(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            this.form.menuIds = this.getMenuAllCheckedKeys();
            addRole(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const roleIds = row.roleId || this.ids;
      this.$confirm('是否确认删除角色编号为"' + roleIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delRole(roleIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有角色数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportRole(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },
        showW1Method(data){
      this.a011 = data.wk1Day1Date;
       this.a012 = data.wk1Day2Date;
        this.a013 = data.wk1Day3Date;
         this.a014 = data.wk1Day4Date;
          this.a015 = data.wk1Day5Date;
           this.a016 = data.wk1Day6Date;
            this.a017 = data.wk1Day7Date;
      if(this.showWk29 === false){
        this.showWk29 = true;
              this.b291 = false;
              this.b292 = true;
      } else if(this.showWk29 === true){
        this.showWk29 = false;
         this.b291 = true;
              this.b292 = false;
      }
    },
        showWk2Method(data){
      this.a021 = data.wk2Day1Date;
       this.a022 = data.wk2Day2Date;
        this.a023 = data.wk2Day3Date;
         this.a024 = data.wk2Day4Date;
          this.a025 = data.wk2Day5Date;
           this.a026 = data.wk2Day6Date;
            this.a027 = data.wk2Day7Date;
      if(this.showWk2 === false){
        this.showWk2 = true;
              this.b021 = false;
              this.b022 = true;
      } else if(this.showWk2 === true){
        this.showWk2 = false;
         this.b021 = true;
              this.b022 = false;
      }
    },
        showWk3Method(data){
      this.a031 = data.wk3Day1Date;
       this.a032 = data.wk3Day2Date;
        this.a033 = data.wk3Day3Date;
         this.a034 = data.wk3Day4Date;
          this.a035 = data.wk3Day5Date;
           this.a036 = data.wk3Day6Date;
            this.a037 = data.wk3Day7Date;
      if(this.showWk3 === false){
        this.showWk3 = true;
              this.b031 = false;
              this.b032 = true;
      } else if(this.showWk3 === true){
        this.showWk3 = false;
         this.b031 = true;
              this.b032 = false;
      }
    },
        showWk4Method(data){
      this.a041 = data.wk4Day1Date;
       this.a042 = data.wk4Day2Date;
        this.a043 = data.wk4Day3Date;
         this.a044 = data.wk4Day4Date;
          this.a045 = data.wk4Day5Date;
           this.a046 = data.wk4Day6Date;
            this.a047 = data.wk4Day7Date;
      if(this.showWk4 === false){
        this.showWk4 = true;
              this.b041 = false;
              this.b042 = true;
      } else if(this.showWk4 === true){
        this.showWk4 = false;
         this.b041 = true;
              this.b042 = false;
      }
    },
        showWk5Method(data){
      this.a051 = data.wk5Day1Date;
       this.a052 = data.wk5Day2Date;
        this.a053 = data.wk5Day3Date;
         this.a054 = data.wk5Day4Date;
          this.a055 = data.wk5Day5Date;
           this.a056 = data.wk5Day6Date;
            this.a057 = data.wk5Day7Date;
      if(this.showWk5 === false){
        this.showWk5 = true;
              this.b051 = false;
              this.b052 = true;
      } else if(this.showWk5 === true){
        this.showWk5 = false;
         this.b051 = true;
              this.b052 = false;
      }
    },
        showWk6Method(data){
      this.a061 = data.wk6Day1Date;
       this.a062 = data.wk6Day2Date;
        this.a063 = data.wk6Day3Date;
         this.a064 = data.wk6Day4Date;
          this.a065 = data.wk6Day5Date;
           this.a066 = data.wk6Day6Date;
            this.a067 = data.wk6Day7Date;
      if(this.showWk6 === false){
        this.showWk6 = true;
              this.b061 = false;
              this.b062 = true;
      } else if(this.showWk6 === true){
        this.showWk6 = false;
         this.b061 = true;
              this.b062 = false;
      }
    },
        showWk7Method(data){
      this.a071 = data.wk7Day1Date;
       this.a072 = data.wk7Day2Date;
        this.a073 = data.wk7Day3Date;
         this.a074 = data.wk7Day4Date;
          this.a075 = data.wk7Day5Date;
           this.a076 = data.wk7Day6Date;
            this.a077 = data.wk7Day7Date;
      if(this.showWk7 === false){
        this.showWk7 = true;
              this.b071 = false;
              this.b072 = true;
      } else if(this.showWk7 === true){
        this.showWk7 = false;
         this.b071 = true;
              this.b072 = false;
      }
    },
        showWk8Method(data){
      this.a081 = data.wk8Day1Date;
       this.a082 = data.wk8Day2Date;
        this.a083 = data.wk8Day3Date;
         this.a084 = data.wk8Day4Date;
          this.a085 = data.wk8Day5Date;
           this.a086 = data.wk8Day6Date;
            this.a087 = data.wk8Day7Date;
      if(this.showWk8 === false){
        this.showWk8 = true;
              this.b081 = false;
              this.b082 = true;
      } else if(this.showWk8 === true){
        this.showWk8 = false;
         this.b081 = true;
              this.b082 = false;
      }
    },
        showWk9Method(data){
      this.a091 = data.wk9Day1Date;
       this.a092 = data.wk9Day2Date;
        this.a093 = data.wk9Day3Date;
         this.a094 = data.wk9Day4Date;
          this.a095 = data.wk9Day5Date;
           this.a096 = data.wk9Day6Date;
            this.a097 = data.wk9Day7Date;
      if(this.showWk9 === false){
        this.showWk9 = true;
              this.b091 = false;
              this.b092 = true;
      } else if(this.showWk9 === true){
        this.showWk9 = false;
         this.b091 = true;
              this.b092 = false;
      }
    },
        showWk10Method(data){
      this.a101 = data.wk10Day1Date;
       this.a102 = data.wk10Day2Date;
        this.a103 = data.wk10Day3Date;
         this.a104 = data.wk10Day4Date;
          this.a105 = data.wk10Day5Date;
           this.a106 = data.wk10Day6Date;
            this.a107 = data.wk10Day7Date;
      if(this.showWk10 === false){
        this.showWk10 = true;
              this.b101 = false;
              this.b102 = true;
      } else if(this.showWk10 === true){
        this.showWk10 = false;
         this.b101 = true;
              this.b102 = false;
      }
    },
        showWk11Method(data){
      this.a111 = data.wk11Day1Date;
       this.a112 = data.wk11Day2Date;
        this.a113 = data.wk11Day3Date;
         this.a114 = data.wk11Day4Date;
          this.a115 = data.wk11Day5Date;
           this.a116 = data.wk11Day6Date;
            this.a117 = data.wk11Day7Date;
      if(this.showWk11 === false){
        this.showWk11 = true;
              this.b111 = false;
              this.b112 = true;
      } else if(this.showWk11 === true){
        this.showWk11 = false;
         this.b111 = true;
              this.b112 = false;
      }
    },
        showWk12Method(data){
      this.a121 = data.wk12Day1Date;
       this.a122 = data.wk12Day2Date;
        this.a123 = data.wk12Day3Date;
         this.a124 = data.wk12Day4Date;
          this.a125 = data.wk12Day5Date;
           this.a126 = data.wk12Day6Date;
            this.a127 = data.wk12Day7Date;
      if(this.showWk12 === false){
        this.showWk12 = true;
              this.b121 = false;
              this.b122 = true;
      } else if(this.showWk12 === true){
        this.showWk12 = false;
         this.b121 = true;
              this.b122 = false;
      }
    },
        showWk13Method(data){
      this.a131 = data.wk13Day1Date;
       this.a132 = data.wk13Day2Date;
        this.a133 = data.wk13Day3Date;
         this.a134 = data.wk13Day4Date;
          this.a135 = data.wk13Day5Date;
           this.a136 = data.wk13Day6Date;
            this.a137 = data.wk13Day7Date;
      if(this.showWk13 === false){
        this.showWk13 = true;
              this.b131 = false;
              this.b132 = true;
      } else if(this.showWk13 === true){
        this.showWk13 = false;
         this.b131 = true;
              this.b132 = false;
      }
    },
        showWk14Method(data){
      this.a141 = data.wk14Day1Date;
       this.a142 = data.wk14Day2Date;
        this.a143 = data.wk14Day3Date;
         this.a144 = data.wk14Day4Date;
          this.a145 = data.wk14Day5Date;
           this.a146 = data.wk14Day6Date;
            this.a147 = data.wk14Day7Date;
      if(this.showWk14 === false){
        this.showWk14 = true;
              this.b141 = false;
              this.b142 = true;
      } else if(this.showWk14 === true){
        this.showWk14 = false;
         this.b141 = true;
              this.b142 = false;
      }
    },
        showWk15Method(data){
      this.a151 = data.wk15Day1Date;
       this.a152 = data.wk15Day2Date;
        this.a153 = data.wk15Day3Date;
         this.a154 = data.wk15Day4Date;
          this.a155 = data.wk15Day5Date;
           this.a156 = data.wk15Day6Date;
            this.a157 = data.wk15Day7Date;
      if(this.showWk15 === false){
        this.showWk15 = true;
              this.b151 = false;
              this.b152 = true;
      } else if(this.showWk15 === true){
        this.showWk15 = false;
         this.b151 = true;
              this.b152 = false;
      }
    },
        showWk16Method(data){
      this.a161 = data.wk16Day1Date;
       this.a162 = data.wk16Day2Date;
        this.a163 = data.wk16Day3Date;
         this.a164 = data.wk16Day4Date;
          this.a165 = data.wk16Day5Date;
           this.a166 = data.wk16Day6Date;
            this.a167 = data.wk16Day7Date;
      if(this.showWk16 === false){
        this.showWk16 = true;
              this.b161 = false;
              this.b162 = true;
      } else if(this.showWk16 === true){
        this.showWk16 = false;
         this.b161 = true;
              this.b162 = false;
      }
    },
        showWk17Method(data){
      this.a171 = data.wk17Day1Date;
       this.a172 = data.wk17Day2Date;
        this.a173 = data.wk17Day3Date;
         this.a174 = data.wk17Day4Date;
          this.a175 = data.wk17Day5Date;
           this.a176 = data.wk17Day6Date;
            this.a177 = data.wk17Day7Date;
      if(this.showWk17 === false){
        this.showWk17 = true;
              this.b171 = false;
              this.b172 = true;
      } else if(this.showWk17 === true){
        this.showWk17 = false;
         this.b171 = true;
              this.b172 = false;
      }
    },
        showWk18Method(data){
      this.a181 = data.wk18Day1Date;
       this.a182 = data.wk18Day2Date;
        this.a183 = data.wk18Day3Date;
         this.a184 = data.wk18Day4Date;
          this.a185 = data.wk18Day5Date;
           this.a186 = data.wk18Day6Date;
            this.a187 = data.wk18Day7Date;
      if(this.showWk18 === false){
        this.showWk18 = true;
              this.b181 = false;
              this.b182 = true;
      } else if(this.showWk18 === true){
        this.showWk18 = false;
         this.b181 = true;
              this.b182 = false;
      }
    },
        showWk19Method(data){
      this.a191 = data.wk19Day1Date;
       this.a192 = data.wk19Day2Date;
        this.a193 = data.wk19Day3Date;
         this.a194 = data.wk19Day4Date;
          this.a195 = data.wk19Day5Date;
           this.a196 = data.wk19Day6Date;
            this.a197 = data.wk19Day7Date;
      if(this.showWk19 === false){
        this.showWk19 = true;
              this.b191 = false;
              this.b192 = true;
      } else if(this.showWk19 === true){
        this.showWk19 = false;
         this.b191 = true;
              this.b192 = false;
      }
    },
        showWk20Method(data){
      this.a201 = data.wk20Day1Date;
       this.a202 = data.wk20Day2Date;
        this.a203 = data.wk20Day3Date;
         this.a204 = data.wk20Day4Date;
          this.a205 = data.wk20Day5Date;
           this.a206 = data.wk20Day6Date;
            this.a207 = data.wk20Day7Date;
      if(this.showWk20 === false){
        this.showWk20 = true;
              this.b201 = false;
              this.b202 = true;
      } else if(this.showWk20 === true){
        this.showWk20 = false;
         this.b201 = true;
              this.b202 = false;
      }
    },
        showWk21Method(data){
      this.a211 = data.wk21Day1Date;
       this.a212 = data.wk21Day2Date;
        this.a213 = data.wk21Day3Date;
         this.a214 = data.wk21Day4Date;
          this.a215 = data.wk21Day5Date;
           this.a216 = data.wk21Day6Date;
            this.a217 = data.wk21Day7Date;
      if(this.showWk21 === false){
        this.showWk21 = true;
              this.b211 = false;
              this.b212 = true;
      } else if(this.showWk21 === true){
        this.showWk21 = false;
         this.b211 = true;
              this.b212 = false;
      }
    },
        showWk22Method(data){
      this.a221 = data.wk22Day1Date;
       this.a222 = data.wk22Day2Date;
        this.a223 = data.wk22Day3Date;
         this.a224 = data.wk22Day4Date;
          this.a225 = data.wk22Day5Date;
           this.a226 = data.wk22Day6Date;
            this.a227 = data.wk22Day7Date;
      if(this.showWk22 === false){
        this.showWk22 = true;
              this.b221 = false;
              this.b222 = true;
      } else if(this.showWk22 === true){
        this.showWk22 = false;
         this.b221 = true;
              this.b222 = false;
      }
    },
        showWk23Method(data){
      this.a231 = data.wk23Day1Date;
       this.a232 = data.wk23Day2Date;
        this.a233 = data.wk23Day3Date;
         this.a234 = data.wk23Day4Date;
          this.a235 = data.wk23Day5Date;
           this.a236 = data.wk23Day6Date;
            this.a237 = data.wk23Day7Date;
      if(this.showWk23 === false){
        this.showWk23 = true;
              this.b231 = false;
              this.b232 = true;
      } else if(this.showWk23 === true){
        this.showWk23 = false;
         this.b231 = true;
              this.b232 = false;
      }
    },
        showWk24Method(data){
      this.a241 = data.wk24Day1Date;
       this.a242 = data.wk24Day2Date;
        this.a243 = data.wk24Day3Date;
         this.a244 = data.wk24Day4Date;
          this.a245 = data.wk24Day5Date;
           this.a246 = data.wk24Day6Date;
            this.a247 = data.wk24Day7Date;
      if(this.showWk24 === false){
        this.showWk24 = true;
              this.b241 = false;
              this.b242 = true;
      } else if(this.showWk24 === true){
        this.showWk24 = false;
         this.b241 = true;
              this.b242 = false;
      }
    },
        showWk25Method(data){
      this.a251 = data.wk25Day1Date;
       this.a252 = data.wk25Day2Date;
        this.a253 = data.wk25Day3Date;
         this.a254 = data.wk25Day4Date;
          this.a255 = data.wk25Day5Date;
           this.a256 = data.wk25Day6Date;
            this.a257 = data.wk25ay7Date;
      if(this.showWk25 === false){
        this.showWk25 = true;
              this.b251 = false;
              this.b252 = true;
      } else if(this.showWk25 === true){
        this.showWk25 = false;
         this.b251 = true;
              this.b252 = false;
      }
    },
        showWk26Method(data){
      this.a261 = data.wk26Day1Date;
       this.a262 = data.wk26Day2Date;
        this.a263 = data.wk26Day3Date;
         this.a264 = data.wk26Day4Date;
          this.a265 = data.wk26Day5Date;
           this.a266 = data.wk26Day6Date;
            this.a267 = data.wk26Day7Date;
      if(this.showWk26 === false){
        this.showWk26 = true;
              this.b261 = false;
              this.b262 = true;
      } else if(this.showWk26 === true){
        this.showWk26 = false;
         this.b261 = true;
              this.b262 = false;
      }
    },
        showWk27Method(data){
      this.a271 = data.wk27Day1Date;
       this.a272 = data.wk27Day2Date;
        this.a273 = data.wk27Day3Date;
         this.a274 = data.wk27Day4Date;
          this.a275 = data.wk27Day5Date;
           this.a276 = data.wk27Day6Date;
            this.a277 = data.wk27Day7Date;
      if(this.showWk27 === false){
        this.showWk27 = true;
              this.b271 = false;
              this.b272 = true;
      } else if(this.showWk27 === true){
        this.showWk27 = false;
         this.b271 = true;
              this.b272 = false;
      }
    },
        showWk28Method(data){
      this.a281 = data.wk28Day1Date;
       this.a282 = data.wk28Day2Date;
        this.a283 = data.wk28Day3Date;
         this.a284 = data.wk28Day4Date;
          this.a285 = data.wk28Day5Date;
           this.a286 = data.wk28Day6Date;
            this.a287 = data.wk28Day7Date;
      if(this.showWk28 === false){
        this.showWk28 = true;
              this.b281 = false;
              this.b282 = true;
      } else if(this.showWk28 === true){
        this.showWk28 = false;
         this.b281 = true;
              this.b282 = false;
      }
    },
    showWk29Method(data){
      this.a291 = data.wk29Day1Date;
       this.a292 = data.wk29Day2Date;
        this.a293 = data.wk29Day3Date;
         this.a294 = data.wk29Day4Date;
          this.a295 = data.wk29Day5Date;
           this.a296 = data.wk29Day6Date;
            this.a297 = data.wk29Day7Date;
      if(this.showWk29 === false){
        this.showWk29 = true;
              this.b291 = false;
              this.b292 = true;
      } else if(this.showWk29 === true){
        this.showWk29 = false;
         this.b291 = true;
              this.b292 = false;
      }
    },
        showWk30Method(data){
      this.a301 = data.wk30Day1Date;
       this.a302 = data.wk30Day2Date;
        this.a303 = data.wk30Day3Date;
         this.a304 = data.wk30Day4Date;
          this.a305 = data.wk30Day5Date;
           this.a306 = data.wk30Day6Date;
            this.a307 = data.wk30Day7Date;
      if(this.showWk30 === false){
        this.showWk30 = true;
         this.b301 = false;
              this.b302 = true;
      } else if(this.showWk30 === true){
        this.showWk30 = false;
         this.b301 = true;
              this.b302 = false;
      }
    },
          showWk31Method(data){
           this.a311 = data.wk31Day1Date;
       this.a312 = data.wk31Day2Date;
        this.a313 = data.wk31Day3Date;
         this.a314 = data.wk31Day4Date;
          this.a315 = data.wk31Day5Date;
           this.a316 = data.wk31Day6Date;
            this.a317 = data.wk31Day7Date;
      if(this.showWk31 === false){
        this.showWk31 = true;
         this.b311 = false;
              this.b312 = true;
      } else if(this.showWk31 === true){
        this.showWk31 = false;
         this.b311 = true;
              this.b312 = false;
      }
    },
             showWk32Method(data){
      this.a321 = data.wk32Day1Date;
       this.a322 = data.wk32Day2Date;
        this.a323 = data.wk32Day3Date;
         this.a324 = data.wk32Day4Date;
          this.a325 = data.wk32Day5Date;
           this.a326 = data.wk32Day6Date;
            this.a327 = data.wk32Day7Date;
      if(this.showWk32 === false){
        this.showWk32 = true;
         this.b321 = false;
              this.b322 = true;
      } else if(this.showWk32 === true){
        this.showWk32 = false;
         this.b321 = true;
              this.b322 = false;
      }
    },
      showWk33Method(data){
      this.a331 = data.wk33Day1Date;
       this.a332 = data.wk33Day2Date;
        this.a333 = data.wk33Day3Date;
         this.a334 = data.wk33Day4Date;
          this.a335 = data.wk33Day5Date;
           this.a336 = data.wk33Day6Date;
            this.a337 = data.wk33Day7Date;
      if(this.showWk33 === false){
        this.showWk33 = true;
         this.b331 = false;
              this.b332 = true;
      } else if(this.showWk33 === true){
        this.showWk33 = false;
         this.b331 = true;
              this.b332 = false;
      }
    },
          showWk34Method(data){
      this.a341 = data.wk34Day1Date;
       this.a342 = data.wk34Day2Date;
        this.a343 = data.wk34Day3Date;
         this.a344 = data.wk34Day4Date;
          this.a345 = data.wk34Day5Date;
           this.a346 = data.wk34Day6Date;
            this.a347 = data.wk34Day7Date;
      if(this.showWk34 === false){
        this.showWk34 = true;
         this.b341 = false;
              this.b342 = true;
      } else if(this.showWk34 === true){
        this.showWk34 = false;
         this.b341 = true;
              this.b342 = false;
      }
    },
          showWk35Method(data){
      this.a351 = data.wk35Day1Date;
       this.a352 = data.wk35Day2Date;
        this.a353 = data.wk35Day3Date;
         this.a354 = data.wk35Day4Date;
          this.a355 = data.wk35Day5Date;
           this.a356 = data.wk35Day6Date;
            this.a357 = data.wk35Day7Date;
      if(this.showWk35 === false){
        this.showWk35 = true;
         this.b351 = false;
              this.b352 = true;
      } else if(this.showWk35 === true){
        this.showWk35 = false;
         this.b351 = true;
              this.b352 = false;
      }
    },
          showWk36Method(data){
      this.a361 = data.wk36Day1Date;
       this.a362 = data.wk36Day2Date;
        this.a363 = data.wk36Day3Date;
         this.a364 = data.wk36Day4Date;
          this.a365 = data.wk36Day5Date;
           this.a366 = data.wk36Day6Date;
            this.a367 = data.wk36Day7Date;
      if(this.showWk36 === false){
        this.showWk36 = true;
         this.b361 = false;
              this.b362 = true;
      } else if(this.showWk36 === true){
        this.showWk36 = false;
         this.b361 = true;
              this.b362 = false;
      }
    },
          showWk37Method(data){
      this.a371 = data.wk37Day1Date;
       this.a372 = data.wk37Day2Date;
        this.a373 = data.wk37Day3Date;
         this.a374 = data.wk37Day4Date;
          this.a375 = data.wk37Day5Date;
           this.a376 = data.wk37Day6Date;
            this.a377 = data.wk37Day7Date;
      if(this.showWk37 === false){
        this.showWk37 = true;
         this.b371 = false;
              this.b372 = true;
      } else if(this.showWk37 === true){
        this.showWk37 = false;
         this.b371 = true;
              this.b372 = false;
      }
    },
          showWk38Method(data){
      this.a381 = data.wk38Day1Date;
       this.a382 = data.wk38Day2Date;
        this.a383 = data.wk38Day3Date;
         this.a384 = data.wk38Day4Date;
          this.a385 = data.wk38Day5Date;
           this.a386 = data.wk38Day6Date;
            this.a387 = data.wk38Day7Date;
      if(this.showWk38 === false){
        this.showWk38 = true;
         this.b381 = false;
              this.b382 = true;
      } else if(this.showWk38 === true){
        this.showWk38 = false;
         this.b381 = true;
              this.b382 = false;
      }
    },
          showWk39Method(data){
      this.a391 = data.wk39Day1Date;
       this.a392 = data.wk39Day2Date;
        this.a393 = data.wk39Day3Date;
         this.a394 = data.wk39Day4Date;
          this.a395 = data.wk39Day5Date;
           this.a396 = data.wk39Day6Date;
            this.a397 = data.wk39Day7Date;
      if(this.showWk39 === false){
        this.showWk39 = true;
         this.b391 = false;
              this.b392 = true;
      } else if(this.showWk39 === true){
        this.showWk39 = false;
         this.b391 = true;
              this.b392 = false;
      }
    },
          showWk40Method(data){
      this.a401 = data.wk40Day1Date;
       this.a402 = data.wk40Day2Date;
        this.a403 = data.wk40Day3Date;
         this.a404 = data.wk40Day4Date;
          this.a405 = data.wk40Day5Date;
           this.a406 = data.wk40Day6Date;
            this.a407 = data.wk40Day7Date;
      if(this.showWk40 === false){
        this.showWk40 = true;
         this.b401 = false;
              this.b402 = true;
      } else if(this.showWk40 === true){
        this.showWk40 = false;
         this.b401 = true;
              this.b402 = false;
      }
    },
          showWk41Method(data){
      this.a411 = data.wk41Day1Date;
       this.a412 = data.wk41Day2Date;
        this.a413 = data.wk41Day3Date;
         this.a414 = data.wk41Day4Date;
          this.a415 = data.wk41Day5Date;
           this.a416 = data.wk41Day6Date;
            this.a417 = data.wk41Day7Date;
      if(this.showWk41 === false){
        this.showWk41 = true;
         this.b411 = false;
              this.b412 = true;
      } else if(this.showWk41 === true){
        this.showWk41 = false;
         this.b411 = true;
              this.b412 = false;
      }
    },
          showWk42Method(data){
      this.a421 = data.wk42Day1Date;
       this.a422 = data.wk42Day2Date;
        this.a423 = data.wk42Day3Date;
         this.a424 = data.wk42Day4Date;
          this.a425 = data.wk42Day5Date;
           this.a426 = data.wk42Day6Date;
            this.a427 = data.wk42Day7Date;
      if(this.showWk42 === false){
        this.showWk42 = true;
         this.b421 = false;
              this.b422 = true;
      } else if(this.showWk42 === true){
        this.showWk42 = false;
         this.b421 = true;
              this.b422 = false;
      }
    },
          showWk43Method(data){
      this.a431 = data.wk43Day1Date;
       this.a432 = data.wk43Day2Date;
        this.a433 = data.wk43Day3Date;
         this.a434 = data.wk43Day4Date;
          this.a435 = data.wk43Day5Date;
           this.a436 = data.wk43Day6Date;
            this.a437 = data.wk43Day7Date;
      if(this.showWk43 === false){
        this.showWk43 = true;
         this.b431 = false;
              this.b432 = true;
      } else if(this.showWk43 === true){
        this.showWk43 = false;
         this.b431 = true;
              this.b432 = false;
      }
    },
          showWk44Method(data){
      this.a441 = data.wk44Day1Date;
       this.a442 = data.wk44Day2Date;
        this.a443 = data.wk44Day3Date;
         this.a444 = data.wk44Day4Date;
          this.a445 = data.wk44Day5Date;
           this.a446 = data.wk44Day6Date;
            this.a447 = data.wk44ay7Date;
      if(this.showWk44 === false){
        this.showWk44 = true;
         this.b441 = false;
              this.b442 = true;
      } else if(this.showWk44 === true){
        this.showWk44 = false;
         this.b441 = true;
              this.b442 = false;
      }
    },
          showWk45Method(data){
      this.a451 = data.wk45Day1Date;
       this.a452 = data.wk45Day2Date;
        this.a453 = data.wk45Day3Date;
         this.a454 = data.wk45Day4Date;
          this.a455 = data.wk45Day5Date;
           this.a456 = data.wk45Day6Date;
            this.a457 = data.wk45Day7Date;
      if(this.showWk45 === false){
        this.showWk45 = true;
         this.b451 = false;
              this.b452 = true;
      } else if(this.showWk45 === true){
        this.showWk45 = false;
         this.b451 = true;
              this.b452 = false;
      }
    },
          showWk46Method(data){
      this.a461 = data.wk46Day1Date;
       this.a462 = data.wk46Day2Date;
        this.a463 = data.wk46Day3Date;
         this.a464 = data.wk46Day4Date;
          this.a465 = data.wk46Day5Date;
           this.a466 = data.wk46Day6Date;
            this.a467 = data.wk46Day7Date;
      if(this.showWk46 === false){
        this.showWk46 = true;
         this.b461 = false;
              this.b462 = true;
      } else if(this.showWk46 === true){
        this.showWk46 = false;
         this.b461 = true;
              this.b462 = false;
      }
    },
          showWk47Method(data){
      this.a471 = data.wk47Day1Date;
       this.a472 = data.wk47Day2Date;
        this.a473 = data.wk47Day3Date;
         this.a474 = data.wk47Day4Date;
          this.a475 = data.wk47Day5Date;
           this.a476 = data.wk47Day6Date;
            this.a477 = data.wk47Day7Date;
      if(this.showWk47 === false){
        this.showWk47 = true;
         this.b471 = false;
              this.b472 = true;
      } else if(this.showWk47 === true){
        this.showWk47 = false;
         this.b471 = true;
              this.b472 = false;
      }
    },
          showWk48Method(data){
      this.a481 = data.wk48Day1Date;
       this.a482 = data.wk48Day2Date;
        this.a483 = data.wk48Day3Date;
         this.a484 = data.wk48Day4Date;
          this.a485 = data.wk48Day5Date;
           this.a486 = data.wk48Day6Date;
            this.a487 = data.wk48Day7Date;
      if(this.showWk48 === false){
        this.showWk48 = true;
         this.b481 = false;
              this.b482 = true;
      } else if(this.showWk48 === true){
        this.showWk48 = false;
         this.b481 = true;
              this.b482 = false;
      }
    },
          showWk49Method(data){
      this.a491 = data.wk49Day1Date;
       this.a492 = data.wk49Day2Date;
        this.a493 = data.wk49Day3Date;
         this.a494 = data.wk49Day4Date;
          this.a495 = data.wk49Day5Date;
           this.a496 = data.wk49Day6Date;
            this.a497 = data.wk49Day7Date;
      if(this.showWk49 === false){
        this.showWk49 = true;
         this.b491 = false;
              this.b492 = true;
      } else if(this.showWk49 === true){
        this.showWk49 = false;
         this.b491 = true;
              this.b492 = false;
      }
    },
          showWk50Method(data){
      this.a501 = data.wk50Day1Date;
       this.a502 = data.wk50Day2Date;
        this.a503 = data.wk50Day3Date;
         this.a504 = data.wk50Day4Date;
          this.a505 = data.wk50Day5Date;
           this.a506 = data.wk50Day6Date;
            this.a507 = data.wk50Day7Date;
      if(this.showWk50 === false){
        this.showWk50 = true;
         this.b501 = false;
              this.b502 = true;
      } else if(this.showWk50 === true){
        this.showWk50 = false;
         this.b501 = true;
              this.b502 = false;
      }
    },
          showWk51Method(data){
      this.a511 = data.wk51Day1Date;
       this.a512 = data.wk51Day2Date;
        this.a513 = data.wk51Day3Date;
         this.a514 = data.wk51Day4Date;
          this.a515 = data.wk51Day5Date;
           this.a516 = data.wk51Day6Date;
            this.a517 = data.wk51Day7Date;
      if(this.showWk51 === false){
        this.showWk51 = true;
         this.b511 = false;
              this.b512 = true;
      } else if(this.showWk51 === true){
        this.showWk51 = false;
         this.b511 = true;
              this.b512 = false;
      }
    },
          showWk52Method(data){
      this.a521 = data.wk52Day1Date;
       this.a522 = data.wk52Day2Date;
        this.a523 = data.wk52Day3Date;
         this.a524 = data.wk52Day4Date;
          this.a525 = data.wk52Day5Date;
           this.a526 = data.wk52Day6Date;
            this.a527 = data.wk52Day7Date;
      if(this.showWk52 === false){
        this.showWk52 = true;
         this.b521 = false;
              this.b522 = true;
      } else if(this.showWk52 === true){
        this.showWk52 = false;
         this.b521 = true;
              this.b522 = false;
      }
    },
          showWk53Method(data){
      this.a531 = data.wk53Day1Date;
       this.a532 = data.wk53Day2Date;
        this.a533 = data.wk53Day3Date;
         this.a534 = data.wk53Day4Date;
          this.a535 = data.wk53Day5Date;
           this.a536 = data.wk53Day6Date;
            this.a537 = data.wk53Day7Date;
      if(this.showWk53 === false){
        this.showWk53 = true;
         this.b531 = false;
              this.b532 = true;
      } else if(this.showWk53 === true){
        this.showWk53 = false;
         this.b531 = true;
              this.b532 = false;
      }
    },
          showWk30Method(data){
      this.a301 = data.wk30Day1Date;
       this.a302 = data.wk30Day2Date;
        this.a303 = data.wk30Day3Date;
         this.a304 = data.wk30Day4Date;
          this.a305 = data.wk30Day5Date;
           this.a306 = data.wk30Day6Date;
            this.a307 = data.wk30Day7Date;
      if(this.showWk30 === false){
        this.showWk30 = true;
         this.b301 = false;
              this.b302 = true;
      } else if(this.showWk30 === true){
        this.showWk30 = false;
         this.b301 = true;
              this.b302 = false;
      }
    },
          showWk30Method(data){
      this.a301 = data.wk30Day1Date;
       this.a302 = data.wk30Day2Date;
        this.a303 = data.wk30Day3Date;
         this.a304 = data.wk30Day4Date;
          this.a305 = data.wk30Day5Date;
           this.a306 = data.wk30Day6Date;
            this.a307 = data.wk30Day7Date;
      if(this.showWk30 === false){
        this.showWk30 = true;
         this.b301 = false;
              this.b302 = true;
      } else if(this.showWk30 === true){
        this.showWk30 = false;
         this.b301 = true;
              this.b302 = false;
      }
    },
          showWk30Method(data){
      this.a301 = data.wk30Day1Date;
       this.a302 = data.wk30Day2Date;
        this.a303 = data.wk30Day3Date;
         this.a304 = data.wk30Day4Date;
          this.a305 = data.wk30Day5Date;
           this.a306 = data.wk30Day6Date;
            this.a307 = data.wk30Day7Date;
      if(this.showWk30 === false){
        this.showWk30 = true;
         this.b301 = false;
              this.b302 = true;
      } else if(this.showWk30 === true){
        this.showWk30 = false;
         this.b301 = true;
              this.b302 = false;
      }
    },
          showWk30Method(data){
      this.a301 = data.wk30Day1Date;
       this.a302 = data.wk30Day2Date;
        this.a303 = data.wk30Day3Date;
         this.a304 = data.wk30Day4Date;
          this.a305 = data.wk30Day5Date;
           this.a306 = data.wk30Day6Date;
            this.a307 = data.wk30Day7Date;
      if(this.showWk30 === false){
        this.showWk30 = true;
         this.b301 = false;
              this.b302 = true;
      } else if(this.showWk30 === true){
        this.showWk30 = false;
         this.b301 = true;
              this.b302 = false;
      }
    },

  /** 查询sellingprice中所有使用的国家 */
      getCountryListForSelect() {
      this.loading = true;
      getCountryList().then(
        response => {
          this.options = response.data;
          this.loading = false;
        }
      );
    },

  }
};
</script>

