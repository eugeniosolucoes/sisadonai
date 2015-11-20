/**
 * Author:  eugenio
 * Created: 18/11/2015
 */

SELECT DISTINCT pf.`Nome_PFisica`, pf.`Codigo_PFisica`, tu.`Nome_Turma`, mens.`Data_Vencimento`, mens.`Valor_Mensalidade`, mens.`Nosso_Numero`
FROM (((((((`PFisicas` pf
INNER JOIN `Matriculas` m ON m.`Codigo_PFisica` = pf.`Codigo_PFisica`)
INNER JOIN `PeriodosLetivos` pl ON pl.`Codigo_Periodo_Letivo` = m.`Codigo_Periodo_Letivo`)
INNER JOIN `Cursos` c ON c.`Codigo_Curso` = m.`Codigo_Curso`)
INNER JOIN `Series` s ON s.`Codigo_Serie` = m.`Codigo_Serie`)
INNER JOIN `Turnos` t ON t.`Codigo_Turno` = m.`Codigo_Turno`)
INNER JOIN `TurmasEscola` tu ON tu.`Codigo_Curso` = c.`Codigo_Curso` AND tu.`Codigo_Serie`= s.`Codigo_Serie` AND tu.`Codigo_Turno` = t.`Codigo_Turno`)
INNER JOIN `Mensalidades` mens ON mens.`Codigo_Periodo_Letivo` = pl.`Codigo_Periodo_Letivo` 
AND mens.`Codigo_Curso` = c.`Codigo_Curso` 
AND mens.`Codigo_Serie` = s.`Codigo_Serie` 
AND mens.`Codigo_PFisica` = pf.`Codigo_PFisica` )
WHERE 1 = 1 
AND pl.`Codigo_Periodo_Letivo` = '2015' 
AND tu.`Codigo_Turma` = '008'
AND m.`Codigo_Situacao_Aluno` = '01' 
AND MONTH(mens.`Data_Vencimento`) = 11
ORDER BY pf.`Nome_PFisica`;

