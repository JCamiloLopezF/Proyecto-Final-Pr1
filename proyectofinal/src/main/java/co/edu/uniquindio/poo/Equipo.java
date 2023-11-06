/**
 * Registro que agrupa los datos de un Equipo
 * @author Área de programación UQ
 * @since 2023-09
 * 
 * Licencia GNU/GPL V3.0 (URL de la licencia correcta aquí)
 */
package co.edu.uniquindio.poo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Predicate;

import util.AssertionUtil;

public record Equipo(String nombre, Persona representante, Collection<Jugador> jugadores, Collection<Enfrentamiento> enfrentamientos) {
    public Equipo {
        AssertionUtil.ASSERTION.assertion(nombre != null && !nombre.trim().isEmpty(), "El nombre es requerido");
        AssertionUtil.ASSERTION.assertion(representante != null, "El representante es requerido");
    }

    public Equipo(String nombre, Persona representante) {
        this(nombre, representante, new LinkedList<>(), new ArrayList<>());
    }

    /**
     * Permite registrar un jugador en un equipo siempre y cuando no exista ya un jugador registrado en el equipo con el mismo nombre y apellido
     * @param jugador Jugador que se desea registrar.
     */
    public void registrarJugador(Jugador jugador) {
        validarJugadorExiste(jugador);
        jugadores.add(jugador);
    }

    /**
     * Permite buscar un jugador en el equipo basado en su nombre y apellido.
     * @param jugador Jugador que se desea buscar
     * @return Optional con el jugador que coincida con el nombre y apellido del jugador buscado, 
     * o Optional vacío en caso de no encontrar un jugador en el equipo con dicho nombre y apellido.
     */
    public Optional<Jugador> buscarJugador(Jugador jugador) {
        Predicate<Jugador> nombreIgual = j -> j.getNombre().equals(jugador.getNombre());
        Predicate<Jugador> apellidoIgual = j -> j.getApellido().equals(jugador.getApellido());
        return jugadores.stream().filter(nombreIgual.and(apellidoIgual)).findAny();
    }

    /**
     * Valida que no exista ya un jugador registrado con el mismo nombre y apellido, en caso de haberlo genera un AssertionError.
     */
    private void validarJugadorExiste(Jugador jugador) {
        boolean existeJugador = buscarJugador(jugador).isPresent();
        AssertionUtil.ASSERTION.assertion(!existeJugador, "El jugador ya está registrado");
    }
}
