
                      $(document).ready(function(){
                       $("#power_report_btn").click(function(){
                                  $("#report_form").attr("action","/HeatLossSystem/report/power/house");
                              });

                       $("#energy_report_btn").click(function(){
                                  $("#report_form").attr("action","/HeatLossSystem/report/energy/house");
                              });
                      });
