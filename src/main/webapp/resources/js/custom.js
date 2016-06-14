
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


               $('.apartment_report_form').submit(function() {
               let startDate = $("#startDate_report_input").val();
               let endDate = $("#endDate_report_input").val();
                     $('.apartment_report_form .startDate_apartment_input').val(startDate);
                     $('.apartment_report_form .endDate_apartment_input').val(endDate);
               });

                      });
