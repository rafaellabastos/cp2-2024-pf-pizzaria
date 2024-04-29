package br.com.fiap.pizzaria.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "TB_PRODUTO", uniqueConstraints = {
        @UniqueConstraint(name = "UK_PRODUTO_SABOR", columnNames = {"SABOR_PRODUTO"})
})
public class Produto {

    @Id
    @SequenceGenerator(name = "SQ_PRODUTO", sequenceName = "SQ_PRODUTO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PRODUTO")
    @Column(name = "ID_PRODUTO")
    private Long id;

    @Column(name = "NM_PRODUTO")
    private String nome;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "SABOR",
            referencedColumnName = "ID_SABOR",
            foreignKey = @ForeignKey(
                    name = "FK_SABOR_PRODUTO"
            )
    )
    private Sabor sabor;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "PRECO",
            referencedColumnName = "ID_PRECO",
            foreignKey = @ForeignKey(
                    name = "FK_PRECO_PRODUTO"
            )
    )
    private BigDecimal preco;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "TB_PRODUTO_OPCIONAIS",
            joinColumns = {
                    @JoinColumn(
                            name = "PRODUTO",
                            referencedColumnName = "ID_PRODUTO",
                            foreignKey = @ForeignKey(
                                    name = "FK_PRODUTO_OPCIONAIS"
                            )
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "OPCIONAIS",
                            referencedColumnName = "ID_OPCIONAIS",
                            foreignKey = @ForeignKey(
                                    name = "FK_OPCIONAIS_PRODUTO"
                            )
                    )
            }
    )
    private Set<Opcional> opcionais = new LinkedHashSet<>();
}
