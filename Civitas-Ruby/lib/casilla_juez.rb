# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative "diario"
require_relative "mazo_sorpresas"
require_relative "titulo_propiedad"
require_relative "sorpresa"
require_relative "tipo_casilla"
require_relative "tipo_sorpresa"
require_relative "jugador"

module Civitas
  class CasillaJuez < Casilla
    @@carcel = 5
    
    def initialize(numCasillaCarcel, nombre)
      super(nombre)
      @@carcel = numCasillaCarcel
    end
    
    @Override
    def recibejugador(actual, todos)
      if jugadorcorrecto(actual, todos)
        informe(actual, todos)
        todos[actual].encarcelar(@@carcel)
      end
    end
    
    @Override    
    def to_s
      '\nNombre: ' + super.nombre.to_s +
      '\nTipo Casilla: JUEZ' +
      '\nCarcel: ' + @@carcel.to_s      
    end
  end
end
