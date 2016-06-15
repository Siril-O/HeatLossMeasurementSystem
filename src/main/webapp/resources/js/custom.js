
              $(document).ready(function(){
                       $("#power_report_btn").click(function(){
                                  $("#report_form").attr("action","/HeatLossSystem/report/power/house");
                              });

                       $("#energy_report_btn").click(function(){
                                  $("#report_form").attr("action","/HeatLossSystem/report/energy/house");
                              });

                       $("#apartment_build_report_form #power_report_btn").click(function(){
                                  $("#apartment_build_report_form").attr("action","/HeatLossSystem/report/power/apartment");
                              });

                       $("#apartment_build_report_form #energy_report_btn").click(function(){
                                  $("#apartment_build_report_form").attr("action","/HeatLossSystem/report/energy/apartment");
                              });

                       $("#module_build_report_form #power_report_btn").click(function(){
                                  $("#module_build_report_form").attr("action","/HeatLossSystem/report/power/module");
                              });

                       $("#module_build_report_form #energy_report_btn").click(function(){
                                  $("#module_build_report_form").attr("action","/HeatLossSystem/report/energy/module");
                              });



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
