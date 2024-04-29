package br.com.fiap.pizzaria.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_OPCIONAL", uniqueConstraints = {
        @UniqueConstraint(name = "UK_OPCIONAL_NOME", columnNames = "NM_OPCIONAL"),
        @UniqueConstraint(name = "UK_OPCIONAL_SABOR", columnNames = "SABOR")
})
public class Opcional {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SQ_OPCIONAL")
    @SequenceGenerator(name = "SQ_SABOR", sequenceName = "SQ_SABOR", allocationSize = 1)
    @Column(name = "ID_SABOR")
    private Long id;

    @Column(name = "NM_OPCIONAL")
    private String nome;

    @Column(name = "PRECO_OPCIONAL")
    private BigDecimal preco;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinColumn(
            name = "SABOR",
            referencedColumnName = "ID_SABOR",
            foreignKey = @ForeignKey(
                    name = "FK_OPCIONAL_SABOR"
            )
    )
    private Sabor sabor;

}
