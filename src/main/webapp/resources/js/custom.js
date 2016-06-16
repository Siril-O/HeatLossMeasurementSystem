

                function changeFormAction(formId, energyButtonId, powerButtonId, powerURL, energyURL){
                       $(`#${formId} #${powerButtonId}`).click(function(){
                                  $(`#${formId}`).attr("action",powerURL);
                              });
                       $(`#${formId} #${energyButtonId}`).click(function(){
                                  $(`#${formId}`).attr("action",energyURL);
                              });
                }

              $(document).ready(function(){

                       changeFormAction("report_form", "energy_report_btn", "power_report_btn", "/HeatLossSystem/report/power/house", "/HeatLossSystem/report/energy/house");
                       changeFormAction("apartment_build_report_form", "energy_report_btn", "power_report_btn", "/HeatLossSystem/report/power/apartment", "/HeatLossSystem/report/energy/apartment");
                       changeFormAction("module_build_report_form", "energy_report_btn", "power_report_btn", "/HeatLossSystem/report/power/module", "/HeatLossSystem/report/energy/module");

               $('.apartment_report_form').submit(function() {
               let startDate = $("#startDate_report_input").val();
               let endDate = $("#endDate_report_input").val();
                     $('.apartment_report_form .startDate_apartment_input').val(startDate);
                     $('.apartment_report_form .endDate_apartment_input').val(endDate);
               });

               if ($('.paging-container')){

              pageSize = 5;
              showPage = function(page) {
                  $(".paging_content").hide();
                  $(".paging_content").each(function(n) {
                      if (n >= pageSize * (page - 1) && n < pageSize * page)
                          $(this).show();
                  });
              }

              showPage(1);

              $(".pagination li a").click(function() {
                  $(".pagination li a").removeClass("active");
                  $(this).addClass("active");
                  showPage(parseInt($(this).text()))
              });

               };
 });
